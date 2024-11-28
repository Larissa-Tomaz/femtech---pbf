package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtils {

    /**
     * Hashes a plain text password using SHA-256 without a salt.
     * @param password the plain text password
     * @return the hashed password (Base64 encoded)
     */
    public static String hashPassword(String password) {
       
        String hashedPassword = hashData(password);  // Hash the plain text password

        return hashedPassword;
    }

    /**
     * Compares a plain text password with a hashed password.
     * @param password the plain text password
     * @param storedHash the hashed password stored in the database
     * @return true if the password matches, false otherwise
     */
    public static boolean checkPassword(String password, String storedHash) {

        // Hash the input plain text password
        String hashedInput = hashData(password);
        // Compare the hashed input with the stored hash
        return hashedInput.equals(storedHash);
    }

    /**
     * Hashes the user data (excluding the password) using a SHA-256 hash.
     * @param data the user data string to hash
     * @return the hashed data as a Base64 encoded string
     */
    public static String hashData(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(data.getBytes());

            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing data", e);
        }
    }
}
