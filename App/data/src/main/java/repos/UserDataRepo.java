package repos;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import models.AccountData;
import models.FertilityData;
import models.ProfileData;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

import java.util.ArrayList;
import java.util.List;

public class UserDataRepo {
    private final String databaseAddress;
    private MongoClient mongoClient;

    public UserDataRepo() {
        this.databaseAddress = "localhost:27017";
    }

    // Establish a new connection to the MongoDB server and return the database instance
    private MongoDatabase newConnection() {
        String uri = "mongodb://" + this.databaseAddress + "/?retryWrites=true&w=majority";
        this.mongoClient = MongoClients.create(uri);
        return this.mongoClient.getDatabase("pbf"); // The same database used in the previous microservice
    }

    public AccountData getAccountData(String uuid) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

        try {
            Document userDoc = mongoCollection.find(eq("_id", uuid)).first();
            if (userDoc != null) {
                Document accountDataDoc = (Document) userDoc.get("accountData");
                return new AccountData(
                        accountDataDoc.getString("profile_name"),
                        accountDataDoc.getString("registration_date"),
                        accountDataDoc.getString("payment_plan"),
                        accountDataDoc.getBoolean("subscription_status")
                );
            }
        } catch (Exception e) {
            System.err.println("[User Repo] Failed to fetch account data for user: " + uuid);
            e.printStackTrace();
        } finally {
            this.mongoClient.close();
        }
        return null;
    }

    // Method to get profile data
    public ProfileData getProfileData(String uuid) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

        try {
            Document userDoc = mongoCollection.find(eq("_id", uuid)).first();
            if (userDoc != null) {
                Document profileDataDoc = (Document) userDoc.get("profileData");
                return new ProfileData(
                        profileDataDoc.getString("age"),
                        profileDataDoc.getString("menstrual_cycle_length"),
                        profileDataDoc.getString("period_length"),
                        profileDataDoc.getString("main_contraceptive_method")
                );
            }
        } catch (Exception e) {
            System.err.println("[User Repo] Failed to fetch profile data for user: " + uuid);
            e.printStackTrace();
        } finally {
            this.mongoClient.close();
        }
        return null;
    }

    // Method to get fertility logs
    public List<FertilityData> getFertilityLogs(String uuid) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
        List<FertilityData> fertilityDataList = new ArrayList<>();

        try {
            Document userDoc = mongoCollection.find(eq("_id", uuid)).first();
            if (userDoc != null) {
                List<Document> logs = (List<Document>) userDoc.get("fertilityLogs");
                if (logs != null) {
                    for (Document log : logs) {
                        // Explicitly convert List<Double> to List<Float>
                        List<Double> temperatureDoubleList = (List<Double>) log.get("temperature");
                        List<Float> temperatureFloatList = new ArrayList<>();
                
                        if (temperatureDoubleList != null) {
                            for (Double temp : temperatureDoubleList) {
                                temperatureFloatList.add(temp.floatValue());  // Convert each Double to Float
                            }
                        }
                
                        FertilityData data = new FertilityData(
                                log.getString("date"),
                                temperatureFloatList,  // Use the converted Float list
                                (List<Integer>) log.get("bpm"),
                                log.getString("bleeding"),
                                log.getString("sexualActivity")
                        );
                        fertilityDataList.add(data);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[User Repo] Failed to fetch fertility logs for user: " + uuid);
            e.printStackTrace();
        } finally {
            this.mongoClient.close();
        }
        return fertilityDataList;
    }

    // Method to add a new FertilityData log entry for a user
    public void addNewFertilityDataLog(String uuid, FertilityData fertilityData) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

        try {
            // Create a new document for the FertilityData log entry, including the date field
            Document fertilityDocument = new Document("date", fertilityData.getDate())  // Include the date in the document
                    .append("temperature", fertilityData.getTemperature())
                    .append("bpm", fertilityData.getBpm())
                    .append("bleeding", fertilityData.getBleeding())
                    .append("sexualActivity", fertilityData.getSexualActivity());

            // Use $push to add a new FertilityData entry to the fertilityLogs array in the user's document
            mongoCollection.updateOne(eq("_id", uuid), push("fertilityLogs", fertilityDocument));
            System.out.println("[User Repo] New FertilityData log added successfully for user: " + uuid);
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to add new FertilityData log for user: " + uuid);
            e.printStackTrace();
        } finally {
            this.mongoClient.close();
        }
    }

    // Method to check if a user exists by UUID
    public boolean checkUserExists(String uuid) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

        try {
            Document userDoc = mongoCollection.find(eq("_id", uuid)).first();
            return userDoc != null;
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to check user existence by UUID: " + uuid);
            e.printStackTrace();
            return false;
        } finally {
            this.mongoClient.close();
        }
    }

    // Method to delete all fertility logs for a user (if needed)
    public void deleteAllFertilityLogs(String uuid) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

        try {
            mongoCollection.updateOne(eq("_id", uuid), push("fertilityLogs", new Document()));  // Reset the fertilityLogs array to an empty document
            System.out.println("[User Repo] Deleted all fertility logs for user: " + uuid);
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to delete fertility logs for user: " + uuid);
            e.printStackTrace();
        } finally {
            this.mongoClient.close();
        }
    }

    // Helper method to delete a user from the database by UUID
    public boolean deleteUser(String uuid) {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

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
        } finally {
            this.mongoClient.close();
        }
    }

    // New Method: Delete All Users (if needed for testing or cleanup)
    public void deleteAllUsers() {
        MongoDatabase mongoDatabase = this.newConnection();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

        try {
            mongoCollection.deleteMany(new Document());  // Deletes all documents in the "users" collection
            System.out.println("[User Repo] All users deleted successfully.");
        } catch (MongoException e) {
            System.err.println("[User Repo] Failed to delete all users.");
            e.printStackTrace();
        } finally {
            this.mongoClient.close();
        }
    }
}
