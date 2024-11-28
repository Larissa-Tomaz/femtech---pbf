package repos;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import models.User;
import models.UserAccountData;
import models.UserProfileData;
import org.bson.Document;
import org.bson.conversions.Bson;
import pbfProto.Auth.ROLE;

import static com.mongodb.client.model.Filters.eq;

public class UserRepo {
    private final String databaseAddress;
    private final Gson gson;
    private static MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    // Constructor to initialize shared MongoClient and MongoDatabase instance
    public UserRepo() {
        this.databaseAddress = "localhost:27017";
        this.gson = new Gson();
        initializeConnection();
        ensureIndexes();
    }

    // Initializes the shared MongoDB connection with connection pooling
    private void initializeConnection() {
        if (mongoClient == null) {
            synchronized (UserRepo.class) {  // Synchronize for thread safety
                if (mongoClient == null) {
                    String uri = "mongodb://" + this.databaseAddress + "/?retryWrites=true&w=majority";
                    ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();

                    MongoClientSettings settings = MongoClientSettings.builder()
                            .applyConnectionString(new ConnectionString(uri))
                            .applyToConnectionPoolSettings(builder -> builder
                                    .maxSize(50)  // Set maximum pool size
                                    .minSize(10)  // Set minimum pool size
                            )
                            .serverApi(serverApi)
                            .build();

                    mongoClient = MongoClients.create(settings);
                }
            }
        }
        this.mongoDatabase = mongoClient.getDatabase("pbf");
    }

    // Ensure indexes are in place for faster queries
    private void ensureIndexes() {
        MongoCollection<Document> mongoCollection = this.mongoDatabase.getCollection("users");
    
        // _id index is already created by default and unique, so we should not add unique constraint explicitly
        mongoCollection.createIndex(new Document("username", 1), new IndexOptions().unique(true));
    }

    // Save or update a user in the database using atomic operations
    public boolean saveUser(String uuid, User user) {
        MongoCollection<Document> mongoCollection = this.mongoDatabase.getCollection("users");
        try {
            Document userDocument = createUserDocument(uuid, user);
            // Use upsert to insert or update if the document already exists
            UpdateResult result = mongoCollection.replaceOne(eq("_id", uuid), userDocument, new ReplaceOptions().upsert(true));
            System.out.println("[User Repo] User saved/updated successfully with embedded AccountData and ProfileData: " + user.getUsername());
            return result.getUpsertedId() != null || result.getModifiedCount() > 0;
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to save/update user: " + user.getUsername());
            e.printStackTrace();
            return false;
        }
    }

    // Update a user document using optimistic locking
    public boolean updateUser(String uuid, User updatedUser) {
        MongoCollection<Document> mongoCollection = this.mongoDatabase.getCollection("users");
        try {
            // Find the current version of the user document
            Document existingUserDoc = mongoCollection.find(eq("_id", uuid)).first();
            if (existingUserDoc == null) {
                System.err.println("[User Repo] User not found for update: " + uuid);
                return false;
            }

            int currentVersion = existingUserDoc.getInteger("version", 0);

            // Perform an atomic update using optimistic locking
            Bson filter = Filters.and(eq("_id", uuid), eq("version", currentVersion));
            Bson updates = createUpdateDocument(updatedUser, currentVersion + 1);

            // Perform the update operation
            UpdateResult result = mongoCollection.updateOne(filter, updates);
            return result.getMatchedCount() == 1;

        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to update user: " + uuid);
            e.printStackTrace();
            return false;
        }
    }

    // Fetch a user from the database using the unique user identifier (UUID)
    public User fetchUser(String uuid) {
        MongoCollection<Document> mongoCollection = this.mongoDatabase.getCollection("users");
        try {
            Document doc = mongoCollection.find(eq("_id", uuid)).first();
            return (doc != null) ? createUserFromDocument(doc) : null;
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to fetch user by uuid: " + uuid);
            e.printStackTrace();
            return null;
        }
    }

    // Delete a user from the database by UUID
    public boolean deleteUser(String uuid) {
        MongoCollection<Document> mongoCollection = this.mongoDatabase.getCollection("users");
        try {
            long deletedCount = mongoCollection.deleteOne(eq("_id", uuid)).getDeletedCount();
            if (deletedCount > 0) {
                System.out.println("[User Repo] User with UUID " + uuid + " deleted successfully.");
                return true;
            } else {
                System.out.println("[User Repo] No user found with UUID " + uuid + ".");
                return false;
            }
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to delete user with UUID: " + uuid);
            e.printStackTrace();
            return false;
        }
    }

    // Close shared MongoClient instance
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    // Helper method to create a user document
    private Document createUserDocument(String uuid, User user) {
        return new Document("_id", uuid)
                .append("accountData", new Document("profile_name", user.getAccountData().getProfileName())
                        .append("registration_date", user.getAccountData().getRegistrationDate())
                        .append("payment_plan", user.getAccountData().getPaymentPlan())
                        .append("subscription_status", user.getAccountData().getSubscriptionStatus()))
                .append("profileData", new Document("age", user.getProfileData().getAge())
                        .append("menstrual_cycle_length", user.getProfileData().getMenstrualCycleLength())
                        .append("period_length", user.getProfileData().getPeriodLength())
                        .append("main_contraceptive_method", user.getProfileData().getMainContraceptiveMethod()))
                .append("role", user.getRole().name())
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("version", 1);
    }

    // Helper method to create an update document
    private Bson createUpdateDocument(User updatedUser, int newVersion) {
        return Updates.combine(
                Updates.set("accountData", new Document("profile_name", updatedUser.getAccountData().getProfileName())
                        .append("registration_date", updatedUser.getAccountData().getRegistrationDate())
                        .append("payment_plan", updatedUser.getAccountData().getPaymentPlan())
                        .append("subscription_status", updatedUser.getAccountData().getSubscriptionStatus())),
                Updates.set("profileData", new Document("age", updatedUser.getProfileData().getAge())
                        .append("menstrual_cycle_length", updatedUser.getProfileData().getMenstrualCycleLength())
                        .append("period_length", updatedUser.getProfileData().getPeriodLength())
                        .append("main_contraceptive_method", updatedUser.getProfileData().getMainContraceptiveMethod())),
                Updates.set("role", updatedUser.getRole().name()),
                Updates.set("password", updatedUser.getPassword()),
                Updates.set("version", newVersion)
        );
    }

    // Helper method to create a User object from a MongoDB document
    private User createUserFromDocument(Document doc) {
        Document accountDataDoc = (Document) doc.get("accountData");
        Document profileDataDoc = (Document) doc.get("profileData");

        UserAccountData accountData = new UserAccountData(
                accountDataDoc.getString("profile_name"),
                accountDataDoc.getString("registration_date"),
                accountDataDoc.getString("payment_plan"),
                accountDataDoc.getBoolean("subscription_status")
        );

        UserProfileData profileData = new UserProfileData(
                profileDataDoc.getString("age"),
                profileDataDoc.getString("menstrual_cycle_length"),
                profileDataDoc.getString("period_length"),
                profileDataDoc.getString("main_contraceptive_method")
        );

        return new User(
                accountData,
                profileData,
                ROLE.valueOf(doc.getString("role")),
                doc.getString("username"),
                doc.getString("password")
        );
    }
}
