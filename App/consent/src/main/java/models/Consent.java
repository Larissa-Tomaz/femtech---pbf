package models;

import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;
import java.util.List;

public class Consent {
    @JsonProperty("date")
    private String date;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("privacySettings")
    private List<ConsentEntry> privacySettings;

    @JsonCreator
    public Consent(@JsonProperty("date") String date,
                   @JsonProperty("userId") String userId,
                   @JsonProperty("privacySettings") List<ConsentEntry> privacySettings) {
        this.date = date;
        this.userId = userId;
        this.privacySettings = privacySettings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ConsentEntry> getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(List<ConsentEntry> privacySettings) {
        this.privacySettings = privacySettings;
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

