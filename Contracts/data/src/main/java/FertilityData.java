/*
 * SPDX-License-Identifier: Apache-2.0
 */

 import com.owlike.genson.Genson;
 import org.hyperledger.fabric.contract.annotation.DataType;
 import org.hyperledger.fabric.contract.annotation.Property;
 import com.owlike.genson.annotation.JsonProperty;

 import java.util.List;

 
 @DataType()
 public final class FertilityData {

    @Property()
    private String date;

    @Property()
    private List<Float> temperature;
 
    @Property()
    private List<Integer> bpm;
 
    @Property()
    private String bleeding;
 
    @Property()
    private String sexualActivity;
 
     
    public FertilityData(@JsonProperty("date") final String date,
                @JsonProperty("temperature") final List<Float> temperature,
                @JsonProperty("bpm") final List<Integer> bpm,
                @JsonProperty("bleeding") final String bleeding,
                @JsonProperty("sexualActivity") final String sexualActivity) {
        this.date = date;
        this.temperature = temperature;
        this.bpm = bpm;
        this.bleeding = bleeding;
        this.sexualActivity = sexualActivity;
    }

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






