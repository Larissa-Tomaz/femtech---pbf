package models;

import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import pbfProto.Auth.ROLE;
import utils.HashUtils;

public class User {

    @JsonProperty("accountData")
    private UserAccountData accountData;

    @JsonProperty("profileData")
    private UserProfileData profileData;

    @JsonProperty("username")
    private String username;

    @JsonProperty("role")
    private ROLE role;

    @JsonProperty("password")
    private String password;


    @JsonCreator
    public User(@JsonProperty("accountData") UserAccountData accountData, @JsonProperty("profileData")  UserProfileData profileData, @JsonProperty("role") ROLE role, @JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.accountData = accountData;
        this.profileData = profileData;  // Initialize with profile data
        this.role = role;
        this.username = username;
        this.password = password;  // Store the password as is (to be hashed later)
    }

    // Getter and Setter methods for UserAccountData
    public UserAccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(UserAccountData accountData) {
        this.accountData = accountData;
    }

    // Getter and Setter methods for UserProfileData
    public UserProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(UserProfileData profileData) {
        this.profileData = profileData;
    }

    // Getter and Setter methods for role
    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    // Getter and Setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter methods for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Check if the provided plain text password matches the stored (hashed) password
    public boolean checkPassword(String plainTextPassword) {
        return HashUtils.checkPassword(plainTextPassword, this.password);
    }

    // Generate a hash of the user data (account and profile information)
    public String hashUserData() {
        String dataToHash = accountData.toString() + profileData.toString() + username;
        return HashUtils.hashData(dataToHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "accountData=" + accountData +
                ", profileData=" + profileData +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
