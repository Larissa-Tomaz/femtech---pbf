/*
 * SPDX-License-Identifier: Apache-2.0
 */

 import org.hyperledger.fabric.contract.annotation.DataType;
 import org.hyperledger.fabric.contract.annotation.Property;
 import com.owlike.genson.annotation.JsonProperty;
 
 @DataType()
 public final class User {
 
     @Property()
     private UserAccountData accountData;
 
     @Property()
     private UserProfileData profileData;  // New field for storing profile-specific data
 
     @Property()
     private String username;
 
     @Property()
     private String role;
 
     @Property()
     private String password;
 
     // Constructor to include UserAccountData, UserProfileData, role, username, and password
     public User(@JsonProperty("accountData") final UserAccountData accountData,
                 @JsonProperty("profileData") final UserProfileData profileData,
                 @JsonProperty("role") final String role,
                 @JsonProperty("username") final String username,
                 @JsonProperty("password") final String password) {
         this.accountData = accountData;
         this.profileData = profileData;
         this.role = role;
         this.username = username;
         this.password = password;  // Store the password as is
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
     public String getRole() {
         return role;
     }
 
     public void setRole(String role) {
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
 
     @Override
     public String toString() {
         return "User{" +
                 "accountData=" + accountData +
                 ", profileData=" + profileData +
                 ", username='" + username + '\'' +
                 ", role='" + role + '\'' +
                 '}';
     }
 }
 