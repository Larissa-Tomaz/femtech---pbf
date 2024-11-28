package services;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import models.DataAccessLog;
import models.DataLog;
import models.FertilityData;
import models.ProfileData;
import pbfProto.ConsentManager;
import pbfProto.ConsentManager.Consent;

import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class HyperledgerService {

    private final Wallet wallet;
    private final Genson genson;

    private final static String DATA_CHANNEL = "data";
    private final static String DATA_CONTRACT = "data";
    private final static String LOG_CHANNEL = "log";
    private final static String LOG_CONTRACT = "log";

    public HyperledgerService() throws Exception {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
        this.wallet = Wallets.newFileSystemWallet(Paths.get("../registry/wallet"));
        this.genson = new Genson();
    }

    public Contract getContract(Gateway gateway, String channel, String contract) {
        return gateway.getNetwork(channel).getContract(contract);
    }

    public Gateway getGateway() throws IOException {
        Path networkConfigPath = Paths.get("resources", "org1.example.com", "connection-org1.json");
        return Gateway.createBuilder()
                .identity(this.wallet, "dataUser2")
                .networkConfig(networkConfigPath)
                .discovery(true)
                .connect();
    }


    public void submitUserData(FertilityData data, String username)
        throws IOException, ContractException, InterruptedException, TimeoutException {

        Gateway gateway = null;
        try {
            gateway = this.getGateway();
            if (gateway == null) {
                throw new IllegalStateException("Gateway initialization failed.");
            }

            Contract contract = this.getContract(gateway, DATA_CHANNEL, DATA_CONTRACT);
            if (contract == null) {
                throw new IllegalStateException("Contract initialization failed.");
            }

            // Serialize the FertilityData object
            String serializedData = this.genson.serialize(data);
            System.out.println("Serialized Data: " + serializedData);

            // Submit transaction to Hyperledger using JSON string
            contract.submitTransaction("updateDataLog", username, serializedData);
            System.out.println("Transaction submitted successfully for user: " + username);
        } catch (Exception e) {
            System.err.println("Error during transaction submission: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for detailed debugging
            // You can re-throw if needed to stop further execution
            throw e;
        } finally {
            if (gateway != null) {
                gateway.close();
            }
        }
    }


    public List<FertilityData> getFertilityLogs(String username) throws IOException,
        ContractException, InterruptedException, TimeoutException {
        //System.out.println("vou buscar fertility logs para este user:" + username);
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, DATA_CHANNEL, DATA_CONTRACT);

        byte[] result = contract.evaluateTransaction("GetFertilityLogs", username);
        gateway.close();
        //System.out.println("o hyperledger coltou isso:" + new String(result));
        // Deserialize the byte[] result into a List<Consent> object
        List<FertilityData> fertilityLogs = this.genson.deserialize(new String(result), new GenericType<List<FertilityData>>() {});

        // Return the deserialized list of consent history
        return fertilityLogs;
    }   

    

    /*// Fetching logs for a user
    public void getUserData(String username, List<ConsentManager.DataType> dataTypes, ConsentManager.Purpose purpose, String entity) throws IOException, ContractException {
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, DATA_CHANNEL, DATA_CONTRACT);

        byte[] result = contract.evaluateTransaction("GetDataLogsForUser", username, this.genson.serialize(dataTypes));
        gateway.close();

        return this.genson.deserialize(result, new GenericType<List<DataLog>>() {});
    }*/

    public void writeToDataAccessLog(String username, DataAccessLog access) throws IOException, ContractException, InterruptedException, TimeoutException {
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, DATA_CHANNEL, DATA_CONTRACT);
        //System.out.println("entrei no hyperledger");

        //System.out.println(access);
        contract.submitTransaction("CreateDataLog", username, this.genson.serialize(access));
        gateway.close();

        System.out.println("Logged Data Access Request");
    }

    // Deleting data for a user
    public void deleteUserData(String username) throws IOException, ContractException, InterruptedException, TimeoutException {
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, DATA_CHANNEL, DATA_CONTRACT);

        contract.submitTransaction("DeleteUserData", username);
        gateway.close();

        System.out.println("Deleted data for user: " + username);
    }

    // Fetching data access logs
    public List<DataAccessLog> getDataAccessLogs(String username, String startDate, String endDate) throws IOException, ContractException {
        System.out.println("entrei no hyperledger");
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, DATA_CHANNEL, DATA_CONTRACT);

        byte[] result = contract.evaluateTransaction("GetDataLogsForUser", username, startDate, endDate);
        gateway.close();

        //System.out.println(new String(result));

        return this.genson.deserialize(new String(result), new GenericType<List<DataAccessLog>>() {});
    }

}
