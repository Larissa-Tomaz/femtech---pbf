package models;

import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

public class ConsentEntry {

    @JsonProperty("dataType")
    private DataType dataType;

    @JsonProperty("purpose")
    private Purpose purpose;

    @JsonProperty("consentGiven")
    private boolean consentGiven;

    @JsonCreator
    public ConsentEntry(@JsonProperty("dataType") DataType dataType,
                        @JsonProperty("purpose") Purpose purpose,
                        @JsonProperty("consentGiven") boolean consentGiven) {
        this.dataType = dataType;
        this.purpose = purpose;
        this.consentGiven = consentGiven;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }

    @Override
    public String toString() {
        return "ConsentEntry{" +
                "dataType=" + dataType +
                ", purpose=" + purpose +
                ", consentGiven=" + consentGiven +
                '}';
    }
}
