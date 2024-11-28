package models;

public class UserContactData {
    private String phoneNumber;
    private String emailAddress;

    public UserContactData(String phoneNumber, String emailAddress) {
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "UserContactData{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
