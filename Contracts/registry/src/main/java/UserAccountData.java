import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class UserAccountData {

    @Property()
    private String profileName;

    @Property()
    private String registrationDate;

    @Property()
    private String paymentPlan;

    @Property()
    private Boolean subscriptionStatus;

    public UserAccountData(@JsonProperty("profileName") final String profileName,
                           @JsonProperty("registrationDate") final String registrationDate,
                           @JsonProperty("paymentPlan") final String paymentPlan,
                           @JsonProperty("subscriptionStatus") final Boolean subscriptionStatus) {
        this.profileName = profileName;
        this.registrationDate = registrationDate;
        this.paymentPlan = paymentPlan;
        this.subscriptionStatus = subscriptionStatus;
    }

    // Getters and Setters
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public Boolean getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(Boolean subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}
