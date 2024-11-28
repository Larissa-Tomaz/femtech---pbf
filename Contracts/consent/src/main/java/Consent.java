import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.List;

@DataType()
public class Consent {

    @Property()
    private final String date;
    @Property()
    private final String userId;
    @Property()
    private final List<ConsentEntry> privacySettings;

    public Consent(@JsonProperty("date") String date, @JsonProperty("userId") String userId,
                   @JsonProperty("privacySettings") List<ConsentEntry> privacySettings) {
        this.date = date;
        this.userId = userId;
        this.privacySettings = privacySettings;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getDate() {
        return this.date;
    }

    public List<ConsentEntry> getPrivacySettings() {
        return this.privacySettings;
    }

    @Override
    public String toString() {
        return "Consent{" +
                "date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                ", privacySettings=" + privacySettings +
                '}';
    }
}
