import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.hyperledger.fabric.shim.ledger.KeyModification;

import java.util.ArrayList;
import java.util.List;

@Contract(
        name = "data",
        info = @Info(
                title = "Data Contract",
                description = "Data Contract for managing data access logs",
                version = "1.4",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "a.registry@example.com",
                        name = "Larissa Tomaz",
                        url = "https://hyperledger.example.com")))

@Default
public final class Data implements ContractInterface {

    private final Genson genson = new Genson();

    /**
     * Creates a new Data Access Log for a user and stores it in the ledger.
     *
     * @param ctx           The transaction context
     * @param username      The username of the user for whom the log is being created
     * @param dataLogString A JSON string representing the DataAccessLog
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void CreateDataLog(final Context ctx, final String username, final String dataLogString) {
        ChaincodeStub stub = ctx.getStub();

        // Deserialize the input string to a DataAccessLog object
        DataAccessLog dataLog = genson.deserialize(dataLogString, DataAccessLog.class);

        // Construct a unique key for the DataAccessLog using userId and date
        String logKey = "log_" + username + "_" + dataLog.getDate();

        // Serialize the DataAccessLog object and store it in the ledger
        String dataLogJson = genson.serialize(dataLog);
        stub.putStringState(logKey, dataLogJson);

        System.out.println("Successfully created Data Access Log for user: " + username + " with key: " + logKey);
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void updateDataLog(final Context ctx, final String username, final String dataString) {

        ChaincodeStub stub = ctx.getStub();
        String userState = stub.getStringState(username);
        
        stub.putStringState(username, dataString);

    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public FertilityData[] GetFertilityLogs(final Context ctx, final String userId) {
        ChaincodeStub stub = ctx.getStub();

        QueryResultsIterator<KeyModification> logResults = stub.getHistoryForKey(userId);

        List<FertilityData> fertilityLogs = new ArrayList<>();

        for (KeyModification modification : logResults) {
            // Deserialize each historical value into a Consent object
            FertilityData fertilityData = genson.deserialize(modification.getStringValue(), FertilityData.class);
            fertilityLogs.add(fertilityData);
        }

        return fertilityLogs.toArray(new FertilityData[0]);
    }


    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public DataAccessLog[] GetDataLogsForUser(final Context ctx, final String username) {
        ChaincodeStub stub = ctx.getStub();

        // Use a more general query to fetch all keys (adjust this as needed for better performance)
        QueryResultsIterator<KeyValue> queryResults = stub.getStateByRange("", "");

        List<DataAccessLog> logsList = new ArrayList<>();

        // Iterate through the results and filter keys containing "log_" + username
        for (KeyValue keyValue : queryResults) {
            System.out.println("Checking key: " + keyValue.getKey()); // Debugging: Log each key
            if (keyValue.getKey().contains("log_" + username)) {
                DataAccessLog dataLog = genson.deserialize(keyValue.getStringValue(), DataAccessLog.class);
                logsList.add(dataLog);
            }
        }

        System.out.println("Fetched " + logsList.size() + " data access logs for user: " + username);

        // Return the results as an array of DataAccessLog
        return logsList.toArray(new DataAccessLog[0]);
    }


    
}
