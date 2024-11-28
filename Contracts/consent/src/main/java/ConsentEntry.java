/*
 * SPDX-License-Identifier: Apache-2.0
 */

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.List;

@DataType()
public class ConsentEntry {

    private static final Genson genson = new Genson();

    @Property
    private String dataType;

    @Property
    private String purpose;

    @Property
    private boolean consentGiven;

    public ConsentEntry(@JsonProperty("dataType") String dataType, @JsonProperty("purpose") String purpose,
                        @JsonProperty("consentGiven") boolean consentGiven) {
        this.dataType = dataType;
        this.purpose = purpose;
        this.consentGiven = consentGiven;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
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
