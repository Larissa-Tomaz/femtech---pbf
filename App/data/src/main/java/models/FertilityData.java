package models;

import com.owlike.genson.annotation.JsonProperty;
import java.util.List;

public class FertilityData {
    @JsonProperty("date")
    private String date;  

    @JsonProperty("temperature")
    private List<Float> temperature;

    @JsonProperty("bpm")
    private List<Integer> bpm;

    @JsonProperty("bleeding")
    private String bleeding;

    @JsonProperty("sexualActivity")
    private String sexualActivity;

    // Updated constructor to include the date field
    public FertilityData(@JsonProperty("date") String date,
                         @JsonProperty("temperature") List<Float> temperature,
                         @JsonProperty("bpm") List<Integer> bpm,
                         @JsonProperty("bleeding") String bleeding,
                         @JsonProperty("sexualActivity") String sexualActivity) {
        this.date = date;
        this.temperature = temperature;
        this.bpm = bpm;
        this.bleeding = bleeding;
        this.sexualActivity = sexualActivity;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Float> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Float> temperature) {
        this.temperature = temperature;
    }

    public List<Integer> getBpm() {
        return bpm;
    }

    public void setBpm(List<Integer> bpm) {
        this.bpm = bpm;
    }

    public String getBleeding() {
        return bleeding;
    }

    public void setBleeding(String bleeding) {
        this.bleeding = bleeding;
    }

    public String getSexualActivity() {
        return sexualActivity;
    }

    public void setSexualActivity(String sexualActivity) {
        this.sexualActivity = sexualActivity;
    }

    @Override
    public String toString() {
        return "FertilityData{" +
                "date='" + date + '\'' +  // Include the date in the toString method
                ", temperature=" + temperature +
                ", bpm=" + bpm +
                ", bleeding='" + bleeding + '\'' +
                ", sexualActivity='" + sexualActivity + '\'' +
                '}';
    }
}
