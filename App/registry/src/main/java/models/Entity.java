package models;

import utils.HashUtils;


public class Entity {
    private String username;
    private String password;  // This stores the full bcrypt hash, including the salt
    private String registrationDate;
    private String role;


    public Entity(String name, String registrationDate, String role, String username, String password) {
        this.registrationDate = registrationDate;
        this.role = role;
        this.username = username;
        this.setPassword(password); // Hashes the password and sets it
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password, hashing it using HashUtils (which uses bcrypt).
     * @param password the plain text password
     */
    public void setPassword(String password) {
        this.password = HashUtils.hashPassword(password);
    }

    /**
     * Checks if the provided plain text password matches the hashed password.
     * @param password the plain text password
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String password) {
        return HashUtils.checkPassword(password, this.password);
    }

    public String hashUserData() {
        // Combine the account data, contact data, and role into a single string
        String dataToHash = username.toString();
        
        // Use the hashData method to hash the combined string
        return HashUtils.hashData(dataToHash);
    }

    @Override
    public String toString() {
        return "Entity{username=" + username +
                '}';
    }
}


