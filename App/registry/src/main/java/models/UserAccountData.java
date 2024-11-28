package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

public class UserAccountData {
    @JsonProperty("profileName")  // Matches "profileName" in JSON
    private String profileName;

    @JsonProperty("registrationDate")  // Matches "registrationDate" in JSON
    private String registrationDate;

    @JsonProperty("paymentPlan")  // Matches "paymentPlan" in JSON
    private String paymentPlan;

    @JsonProperty("subscriptionStatus")  // Matches "subscriptionStatus" in JSON
    private Boolean subscriptionStatus;

    @JsonCreator
    public UserAccountData(@JsonProperty("profileName") String profileName, @JsonProperty("registrationDate") String registrationDate, @JsonProperty("paymentPlan") String paymentPlan, @JsonProperty("subscriptionStatus") Boolean subscriptionStatus) {
        this.profileName = profileName;
        this.registrationDate = registrationDate;
        this.paymentPlan = paymentPlan;
        this.subscriptionStatus = subscriptionStatus;
    }

    // Constructor with essential fields
    public UserAccountData(String profileName, String registrationDate) {
        this.profileName = profileName;
        this.registrationDate = registrationDate;
    }

    // Getter and Setter for profileName
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    // Getter and Setter for registrationDate
    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Getter and Setter for paymentPlan
    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    // Getter and Setter for subscriptionStatus
    public Boolean getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(Boolean subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    @Override
    public String toString() {
        return "UserAccountData{" +
                "profileName='" + profileName + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", paymentPlan='" + paymentPlan + '\'' +
                ", subscriptionStatus=" + subscriptionStatus +
                '}';
    }
}
