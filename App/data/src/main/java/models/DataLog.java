package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataLog {
    @JsonProperty("date")
    private String date;

    @JsonProperty("fertilityData")
    private FertilityData fertilityData;

    @JsonProperty("profileData")
    private ProfileData profileData;

    // Constructor
    public DataLog(@JsonProperty("date") String date,
                   @JsonProperty("fertilityData") FertilityData fertilityData,
                   @JsonProperty("profileData") ProfileData profileData) {
        this.date = date;
        this.fertilityData = fertilityData;
        this.profileData = profileData;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FertilityData getFertilityData() {
        return fertilityData;
    }

    public void setFertilityData(FertilityData fertilityData) {
        this.fertilityData = fertilityData;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }

    @Override
    public String toString() {
        return "DataLog{" +
                "date='" + date + '\'' +
                ", fertilityData=" + fertilityData +
                ", profileData=" + profileData +
                '}';
    }
}
