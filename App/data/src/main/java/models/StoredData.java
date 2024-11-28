package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StoredData {

    @JsonProperty("accountData")
    private AccountData accountData;

    @JsonProperty("profileData")
    private ProfileData profileData;

    @JsonProperty("fertilityDailyLogs")
    private List<FertilityData> fertilityDailyLogs;

    // Constructor
    public StoredData(@JsonProperty("accountData") AccountData accountData,
                      @JsonProperty("profileData") ProfileData profileData,
                      @JsonProperty("fertilityDailyLogs") List<FertilityData> fertilityDailyLogs) {
        this.accountData = accountData;
        this.profileData = profileData;
        this.fertilityDailyLogs = fertilityDailyLogs;
    }

    // Getters and Setters
    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }

    public List<FertilityData> getFertilityDailyLogs() {
        return fertilityDailyLogs;
    }

    public void setFertilityDailyLogs(List<FertilityData> fertilityDailyLogs) {
        this.fertilityDailyLogs = fertilityDailyLogs;
    }

    @Override
    public String toString() {
        return "StoredData{" +
                "accountData=" + accountData +
                ", profileData=" + profileData +
                ", fertilityDailyLogs=" + fertilityDailyLogs +
                '}';
    }
}

