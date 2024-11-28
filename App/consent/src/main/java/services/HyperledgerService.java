package services;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import models.*;
import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class HyperledgerService {

    private final Wallet wallet;
    private final Genson genson;

    private final static String CONSENT_CHANNEL = "consent";
    private final static String CONSENT_CONTRACT = "consent";

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
        //System.out.println("network config path"+networkConfigPath);
        return Gateway.createBuilder()
                .identity(this.wallet, "consentUser2")
                .networkConfig(networkConfigPath)
                .discovery(true)
                .connect();
    }

    public Consent setConsent(String userId, String date, List<ConsentEntry> privacySettings ) throws IOException,
        ContractException, InterruptedException, TimeoutException {
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, CONSENT_CHANNEL, CONSENT_CONTRACT);

        Consent consent = new Consent(
        date, 
        userId,
        privacySettings
        );

        //String serializedConsent = this.genson.serialize(consent);
        //System.out.println("Serialized Consent: " + serializedConsent);
        contract.submitTransaction("SetConsent", userId, this.genson.serialize(consent));

        
        gateway.close();

        return consent;
    } 

    public List<DataType> verifyConsent(String userId, Purpose purpose) throws IOException, 
        ContractException, InterruptedException, TimeoutException {
        //System.out.println("Entering Hyperledger verify consent method...");

        // Establish connection to Hyperledger Fabric Gateway
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, CONSENT_CHANNEL, CONSENT_CONTRACT);
        
        //System.out.println("Invoking the contract...");
        // Execute the chaincode transaction with the appropriate parameters
        byte[] result = contract.evaluateTransaction("VerifyConsent", userId, purpose.name());
        
        // Print the raw result from the chaincode
        String resultString = new String(result);
        //System.out.println("Raw result from chaincode: " + resultString);

        // Deserialize based on expected return type: List<String>
        List<String> dataTypeStrings = this.genson.deserialize(resultString, new GenericType<List<String>>() {});
        //System.out.println("Deserialized string list: " + dataTypeStrings);

        // Convert the string list to DataType enums
        List<DataType> dataTypes = new ArrayList<>();
        for (String dataTypeStr : dataTypeStrings) {
            try {
                // Assuming DataType is an enum
                dataTypes.add(DataType.valueOf(dataTypeStr));
            } catch (IllegalArgumentException e) {
                System.err.println("Unknown data type received: " + dataTypeStr);
            }
        }

        //System.out.println("Converted data types: " + dataTypes);

        // Close the gateway connection after transaction is complete
        gateway.close();

        // Return the converted list of DataType enums
        return dataTypes;
    }



    public List<Consent> getConsentHistory(String username) throws IOException,
        ContractException, InterruptedException, TimeoutException {
        Gateway gateway = this.getGateway();
        Contract contract = this.getContract(gateway, CONSENT_CHANNEL, CONSENT_CONTRACT);

        byte[] result = contract.evaluateTransaction("GetConsentHistory", username); // ver se é mesmo evaluate transaction
        gateway.close();

        // Deserialize the byte[] result into a List<Consent> object
        List<Consent> consentHistory = this.genson.deserialize(new String(result), new GenericType<List<Consent>>() {});

        // Return the deserialized list of consent history
        return consentHistory;
    }   

    public Consent getConsent(String userId) throws IOException,
        ContractException, InterruptedException, TimeoutException {
        Gateway gateway = this.getGateway();
     
        Contract contract = this.getContract(gateway, CONSENT_CHANNEL, CONSENT_CONTRACT);
       
        byte[] result = contract.evaluateTransaction("GetConsent", userId); 
        //System.out.println("ta aqui o resultado" + new String(result));
        
        gateway.close();
        if (result == null || result.length == 0) {
            System.err.println("No consent data found for user: " + userId);
            return null;
        }
        return this.genson.deserialize(new String(result), Consent.class);

    }

}