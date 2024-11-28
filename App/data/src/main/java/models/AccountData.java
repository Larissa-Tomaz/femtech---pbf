package models;

public class AccountData {
    private String profile_name;
    private String registration_date;
    private String payment_plan;
    private Boolean subscription_status;

    public AccountData(String profile_name, String registration_date, String payment_plan, Boolean subscription_status) {
        this.profile_name = profile_name;
        this.registration_date = registration_date;
        this.payment_plan = payment_plan;
        this.subscription_status = subscription_status;
    }

    public AccountData(String profile_name, String registration_date) {
        this.profile_name = profile_name;
        this.registration_date = registration_date;
    }

    public String getProfileName() {
        return profile_name;
    }

    public void setProfileName(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getRegistrationDate() {
        return registration_date;
    }

    public void setRegistrationDate(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getPaymentPlan() {
        return payment_plan;
    }

    public void setPaymentPlan(String payment_plan) {
        this.payment_plan = payment_plan;
    }

    public Boolean getSubscriptionStatus() {
        return subscription_status;
    }

    public void setSubscriptionStatus(Boolean subscription_status) {
        this.subscription_status = subscription_status;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "profile_name='" + profile_name + '\'' +
                ", registration_date='" + registration_date + '\'' +
                ", payment_plan='" + payment_plan + '\'' +
                ", subscription_status=" + subscription_status +
                '}';
    }
}

