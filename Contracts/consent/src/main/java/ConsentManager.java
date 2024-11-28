import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;

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
        name = "consent",
        info = @Info(
                title = "Consent Contract",
                description = "Consent contract",
                version = "1.0",
                contact = @Contact(
                        email = "servers@larissa.pt",
                        name = "PBF"
                )
        )
)
@Default
public final class ConsentManager implements ContractInterface {

    private final Genson genson = new Genson();

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void SetConsent(final Context ctx, final String userId, final String consentString) {
        ChaincodeStub stub = ctx.getStub();
        stub.putStringState(userId, consentString);
    }


    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Consent GetConsent(final Context ctx, final String userId) {
        ChaincodeStub stub = ctx.getStub();

        // Retrieve the consent string from the ledger using the userId
        String consentJson = stub.getStringState(userId);

        // Check if the consent JSON exists in the state
        if (consentJson == null || consentJson.isEmpty()) {
            System.out.println("[ConsentManager] No consent found for user ID: " + userId);
            return null; // Or throw an exception if you prefer
        }

        // Deserialize the JSON string to a Consent object using Genson
        Consent consent = genson.deserialize(consentJson, Consent.class);

        return consent;
    }


   @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String VerifyConsent(final Context ctx, final String userId, String purpose) {
        ChaincodeStub stub = ctx.getStub();

        // Retrieve the consent JSON from the ledger using the userId
        String consentJson = stub.getStringState(userId);

        if (consentJson == null || consentJson.isEmpty()) {
            System.out.println("[ConsentManager] No consent found for user ID: " + userId);
            return genson.serialize(new ArrayList<>());  // Return an empty list serialized as a JSON string
        }

        Consent consent = genson.deserialize(consentJson, Consent.class);
        List<String> authorizedDataTypes = new ArrayList<>();

        if (consent.getPrivacySettings() != null) {
            for (ConsentEntry entry : consent.getPrivacySettings()) {
                if (entry.getPurpose().equalsIgnoreCase(purpose) && entry.isConsentGiven()) {
                    authorizedDataTypes.add(entry.getDataType());
                }
            }
        }

        System.out.println("Returning authorized data types: " + authorizedDataTypes);

        // Serialize the list of authorized data types as a JSON string to ensure compatibility
        return genson.serialize(authorizedDataTypes);
    }



    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Consent[] GetConsentHistory(final Context ctx, final String userId) {
        ChaincodeStub stub = ctx.getStub();

        // Use getHistoryForKey to retrieve all historical states for the given userId
        QueryResultsIterator<KeyModification> historyResults = stub.getHistoryForKey(userId);

        List<Consent> consentHistory = new ArrayList<>();

        System.out.println("Fetching consent history for user: " + userId);
        for (KeyModification modification : historyResults) {
            // Deserialize each historical value into a Consent object
            Consent consent = genson.deserialize(modification.getStringValue(), Consent.class);
            System.out.println("Historical entry - Tx ID: " + modification.getTxId() + ", Timestamp: " + modification.getTimestamp());
            consentHistory.add(consent);
        }

        return consentHistory.toArray(new Consent[0]);
    }

}
