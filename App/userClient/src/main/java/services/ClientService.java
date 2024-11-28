package services;

import pbfProto.Auth;
import pbfProto.Auth.ROLE;
import pbfProto.Data;
import pbfProto.GatewaysGrpc;
import pbfProto.ConsentManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClientService implements AutoCloseable {

    private final GatewaysGrpc.GatewaysBlockingStub stub;
    private final ManagedChannel channel;
    String userToken;
    String username;

    private final List<Float> temperature = new ArrayList<>(); 
    private final List<Integer> bpm = new ArrayList<>();       
    private String bleeding;                                 
    private String sexualActivity;                             

    public ClientService(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = GatewaysGrpc.newBlockingStub(this.channel);
    }


    public void submitFertilityDailyLog(List<Float> temperatures, List<Integer> bpmValues, String bleeding, String sexualActivity, String date) throws Exception {
        // Create a FertilityDailyLog with the provided values
        Data.FertilityDailyLog fertilityDailyLog = Data.FertilityDailyLog.newBuilder()
                .addAllTemperature(temperatures)
                .addAllBpm(bpmValues)
                .setBleeding(bleeding)
                .setSexualActivity(sexualActivity)
                .setDate(date)
                .build();
    
        // Build the SubmitDataRequest with the FertilityDailyLog and the user's token
        Data.SubmitDataRequest submitDataRequest = Data.SubmitDataRequest.newBuilder()
                .setFertilityDailyLog(fertilityDailyLog)
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Submitting fertility daily log for date: " + date);
    
        // Send the request to the server and handle the response
        Data.SubmitDataResponse response = this.stub.submitData(submitDataRequest);
    
        if (response.hasErrorMessage()) {
            throw new Exception(response.getErrorMessage().getDescription());
        }
    
        System.out.println("[Client Service] Fertility daily log submitted successfully for date: " + date);
    }
    

    public void getUserData(String username, String purpose, String entity) throws Exception {
        // Build the GetDataRequest
        Data.GetDataRequest getDataRequest = Data.GetDataRequest.newBuilder()
                .setUsername(username)
                .setPurpose(ConsentManager.Purpose.valueOf(purpose.toUpperCase()))  // Convert purpose string to enum
                .setEntity(entity)
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Fetching data for user: " + username);
    
        // Send the GetDataRequest to the server and receive the response
        Data.GetDataResponse getDataResponse = this.stub.getUserData(getDataRequest);
    
        // Check for any error in the response
        if (getDataResponse.hasErrorMessage()) {
            throw new Exception(getDataResponse.getErrorMessage().getDescription());
        }
    
        // Retrieve the stored data from the response
        Data.StoredData storedData = getDataResponse.getStoredData();
        System.out.println("[Client Service] Data logs for user: " + username);
    
        // Display User Account Data if available
        if (storedData.hasAccountData()) {
            System.out.println("=== User Account Data ===");
            //System.out.println("Profile Name: " + storedData.getAccountData().getProfileName());
            System.out.println("Registration Date: " + formatConsentDate(storedData.getAccountData().getRegistrationDate()));
            System.out.println("Payment Plan: " + storedData.getAccountData().getPaymentPlan());
            System.out.println("Subscription Status: " + storedData.getAccountData().getSubscriptionStatus());
            System.out.println("=========================");
        }
    
        // Display User Profile Data if available
        if (storedData.hasProfileData()) {
            System.out.println("=== User Profile Data ===");
            System.out.println("Age: " + storedData.getProfileData().getAge());
            System.out.println("Menstrual Cycle Length: " + storedData.getProfileData().getMenstrualCycleLength());
            System.out.println("Period Length: " + storedData.getProfileData().getPeriodLength());
            System.out.println("Main Contraceptive Method: " + storedData.getProfileData().getMainContraceptiveMethod());
            System.out.println("=========================");
        }
    
        // Display each Fertility Daily Log if available
        if (storedData.getFertilityDailyLogCount() > 0) {
            System.out.println("=== Fertility Daily Logs ===");
            for (Data.FertilityDailyLog log : storedData.getFertilityDailyLogList()) {
                System.out.println("Date: " + log.getDate());
                System.out.println("Temperatures: " + log.getTemperatureList());
                System.out.println("BPM Values: " + log.getBpmList());
                System.out.println("Bleeding: " + log.getBleeding());
                System.out.println("Sexual Activity: " + log.getSexualActivity());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No fertility daily logs found.");
        }
    }
    
    
    public void getConsent(String userId, String roleStr) throws Exception {
        // Convert role from string to enum
        Auth.ROLE role = Auth.ROLE.valueOf(roleStr);
    
        // Build the GetConsentRequest
        ConsentManager.GetConsentRequest consentRequest = ConsentManager.GetConsentRequest.newBuilder()
                .setUserId(userId)
                .setRole(role)
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Fetching consent for user: " + userId);
    
        // Send the GetConsentRequest to the server
        ConsentManager.GetConsentResponse consentResponse = this.stub.getConsent(consentRequest);
    
        // Check if the server responded with an error
        if (consentResponse.hasErrorMessage()) {
            throw new Exception(consentResponse.getErrorMessage().getDescription());
        }
    
        // Fetch the consent object from the response
        ConsentManager.Consent consent = consentResponse.getConsentMatrix();
    
        // Prepare and print the consent matrix and sharing matrix
        Map<ConsentManager.DataType, Map<ConsentManager.Purpose, Boolean>> consentTable = new HashMap<>();
    
        for (ConsentManager.ConsentEntry entry : consent.getPrivacySettingsList()) {
            consentTable
                    .computeIfAbsent(entry.getDataType(), k -> new HashMap<>())
                    .put(entry.getPurpose(), entry.getConsentGiven());
    
        }
        String consentDate = consent.getDate();  
        String formattedDate = formatConsentDate(consentDate);
        System.out.println("\nConsent Date: " + formattedDate);  // Display formatted date

        printConsentGivenTable(consentTable);
    
    }
    
    public void getConsentHistory(String userId, String roleStr) throws Exception {
        // Convert role from string to enum
        Auth.ROLE role = Auth.ROLE.valueOf(roleStr);
    
        // Build the GetConsentHistoryRequest
        ConsentManager.GetConsentHistoryRequest consentHistoryRequest = ConsentManager.GetConsentHistoryRequest.newBuilder()
                .setUserId(userId)
                .setRole(role)
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Fetching consent history for user: " + userId);
    
        // Send the request to the server
        ConsentManager.GetConsentHistoryResponse consentHistoryResponse = this.stub.getConsentHistory(consentHistoryRequest);
    
        // Check for error in the response
        if (consentHistoryResponse.hasErrorMessage()) {
            throw new Exception(consentHistoryResponse.getErrorMessage().getDescription());
        }
    
        // Process each consent version in the history
        for (ConsentManager.Consent consent : consentHistoryResponse.getConsentVersionsList()) {
            // Print the date of each consent version
             // Assuming you have the date stored as part of the consentTable metadata or retrieved externally
            String consentDate = consent.getDate();  
            String formattedDate = formatConsentDate(consentDate);
            System.out.println("\nConsent Date: " + formattedDate);  // Display formatted date
    
            
            Map<ConsentManager.DataType, Map<ConsentManager.Purpose, Boolean>> consentTable = new HashMap<>();

            for (ConsentManager.ConsentEntry entry : consent.getPrivacySettingsList()) {
                consentTable
                        .computeIfAbsent(entry.getDataType(), k -> new HashMap<>())
                        .put(entry.getPurpose(), entry.getConsentGiven());
            }
            printConsentGivenTable(consentTable);
    
        }
    }
    

    public void setConsent(Map<String, Map<String, Boolean>> consentSelections) throws Exception {
        List<ConsentManager.ConsentEntry> consentEntries = new ArrayList<>();

        // Iterate through each data type and purpose in the matrix
        for (String dataTypeStr : consentSelections.keySet()) {
            ConsentManager.DataType dataType = ConsentManager.DataType.valueOf(dataTypeStr.toUpperCase());
    
            for (String purposeStr : consentSelections.get(dataTypeStr).keySet()) {
                ConsentManager.Purpose purpose = ConsentManager.Purpose.valueOf(purposeStr.toUpperCase());
                boolean consentGiven = consentSelections.get(dataTypeStr).get(purposeStr);

    
                // Create a ConsentEntry for each selection
                ConsentManager.ConsentEntry consentEntry = ConsentManager.ConsentEntry.newBuilder()
                        .setDataType(dataType)
                        .setPurpose(purpose)
                        .setConsentGiven(consentGiven)
                        .build();
    
                consentEntries.add(consentEntry);
            }
        }
    
        // Build and send the full consent matrix
        ConsentManager.SetConsentRequest setConsentRequest = ConsentManager.SetConsentRequest.newBuilder()
                .addAllPrivacySettings(consentEntries)
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Setting consent matrix for user: " + username);
    
        // Send the request to the server and capture the response
        ConsentManager.SetConsentResponse setConsentResponse = this.stub.setConsent(setConsentRequest);
    
        if (setConsentResponse.hasErrorMessage()) {
            throw new Exception(setConsentResponse.getErrorMessage().getDescription());
        }
    
        System.out.println("[Client Service] Consent matrix set successfully for user: " + username);
    }
    
    
    private void printConsentGivenTable(Map<ConsentManager.DataType, Map<ConsentManager.Purpose, Boolean>> consentTable) {
    
        // Define a specific order for the purposes (columns)
        ConsentManager.Purpose[] orderedPurposes = {
            ConsentManager.Purpose.SERVICE_PROVISION,
            ConsentManager.Purpose.ANALYTICS,
            ConsentManager.Purpose.MARKETING,
            ConsentManager.Purpose.RESEARCH,
            ConsentManager.Purpose.SHARING
        };
    
        // Define a specific order for the data types (rows)
        ConsentManager.DataType[] orderedDataTypes = {
            ConsentManager.DataType.ACCOUNT_DATA,
            ConsentManager.DataType.FERTILITY_DATA,
            ConsentManager.DataType.PROFILE_DATA
        };
    
        // Print Header Row (with custom order)
        System.out.printf("%-20s", "DataType");  // Column header for DataType
        for (ConsentManager.Purpose purpose : orderedPurposes) {
            System.out.printf("%-20s", purpose.name());  // Custom column headers for each purpose
        }
        System.out.println();
    
        // Print Rows for each DataType in custom order
        for (ConsentManager.DataType dataType : orderedDataTypes) {
            System.out.printf("%-20s", dataType.name());  // Print DataType as the first column
            for (ConsentManager.Purpose purpose : orderedPurposes) {
                // Check and print consent value for each purpose within the data type
                Boolean consentGiven = consentTable.getOrDefault(dataType, Collections.emptyMap()).get(purpose);
                String displayValue = (consentGiven != null) ? (consentGiven ? "true" : "false") : "N/A";
                System.out.printf("%-20s", displayValue);
            }
            System.out.println();  // Move to the next line after each DataType
        }
    }
    
    // Helper method to format the date string into a more readable format
    private String formatConsentDate(String consentDate) {
        // Original format: "yyyyMMdd_HHmmss"
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        // Desired format: "dd-MMM-yyyy HH:mm:ss"
        SimpleDateFormat desiredFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    
        try {
            // Parse the original date string
            Date date = originalFormat.parse(consentDate);
            // Return the formatted date
            return desiredFormat.format(date);
        } catch (Exception e) {
            // In case of parsing failure, return the original string
            return consentDate;
        }
    }
    

    public void registerUser(String username, String profileName, String paymentPlan, Boolean subscriptionStatus, String password, String role, String age, String menstrualCycleLength, String periodLength, String mainContraceptiveMethod)
    throws Exception {

        // Build UserProfileData object using the additional profile fields
        Auth.UserProfileData profileData = Auth.UserProfileData.newBuilder()
                .setAge(age)
                .setMenstrualCycleLength(menstrualCycleLength)
                .setPeriodLength(periodLength)
                .setMainContraceptiveMethod(mainContraceptiveMethod)
                .build();

        // Build UserAccountData object
        Auth.UserAccountData accountData = Auth.UserAccountData.newBuilder()
                .setProfileName(profileName)
                .setRegistrationDate(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()))
                .setPaymentPlan(paymentPlan)
                .setSubscriptionStatus(subscriptionStatus)
                .build();

        // Build the UserRegistrationRequest with both account and profile data
        Auth.UserRegistrationRequest userRegistrationRequest = Auth.UserRegistrationRequest.newBuilder()
                .setAccountData(accountData)
                .setUsername(username)
                .setPassword(password)
                .setRole(Auth.ROLE.valueOf(role))
                .setProfileData(profileData)  // Include profile data in the request
                .build();

        System.out.println("[Client Service] Registering user with username: " + username);
        Auth.UserRegistrationResponse userRegistrationResponse = this.stub.registerUser(userRegistrationRequest);

        if (userRegistrationResponse.hasErrorMessage()) {
            throw new Exception(userRegistrationResponse.getErrorMessage().getDescription());
        }
    }

   
    public void loginUser(String username, String password) throws Exception {
        Auth.UserAuthenticationRequest authenticationRequest = Auth.UserAuthenticationRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        System.out.println("[Client Service] Logging in user: " + username);
        Auth.UserAuthenticationResponse authenticationResponse = this.stub.authenticateUser(authenticationRequest);
        if (authenticationResponse.hasErrorMessage()) {
            throw new Exception(authenticationResponse.getErrorMessage().getDescription());
        }

        this.userToken = authenticationResponse.getToken();
        this.username = username;
    }

    public void logoutUser() throws Exception {
        if (this.userToken == null) {
            System.err.println("[Client Service] No user is logged in or the session token is missing.");
            return;  // No user is logged in, so return without proceeding further
        }
    
        Auth.UserLogoutRequest logoutRequest = Auth.UserLogoutRequest.newBuilder()
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Logging out user: " + this.username);
        Auth.UserLogoutResponse logoutResponse = this.stub.logoutUser(logoutRequest);
        if (logoutResponse.hasErrorMessage()) {
            throw new Exception(logoutResponse.getErrorMessage().getDescription());
        }
    
        this.userToken = null;
        this.username = null;
    
        System.out.println("[Client Service] User logged out successfully.");
    }

    // Method to delete a user
    public void deleteUser() throws Exception {
        if(userToken == null){
            System.err.println("[Client Service] No user is logged in.");
        }

        Auth.UserDeleteRequest deleteUserRequest = Auth.UserDeleteRequest.newBuilder()
                .setToken(this.userToken)
                .build();

        System.out.println("[Client Service] Deleting user: " + username);
        Auth.UserDeleteResponse deleteUserResponse = this.stub.deleteUser(deleteUserRequest);

        if (deleteUserResponse.hasErrorMessage()) {
            throw new Exception(deleteUserResponse.getErrorMessage().getDescription());
        }

        System.out.println("[Client Service] User deleted: " + username);
    }

    // Method to delete user data
    public void deleteUserData(String username) throws Exception {
        Data.DeleteDataRequest deleteDataRequest = Data.DeleteDataRequest.newBuilder()
                .setUsername(username)
                .setToken(this.userToken)
                .build();

        System.out.println("[Client Service] Deleting data for user: " + username);

        Data.DeleteDataResponse deleteDataResponse = this.stub.deleteUserData(deleteDataRequest);

        if (deleteDataResponse.hasErrorMessage()) {
            throw new Exception(deleteDataResponse.getErrorMessage().getDescription());
        }

        System.out.println("[Client Service] Data deleted for user: " + username);
    }
    

    public void getDataAccessLogs(String username, String startDate, String endDate) throws Exception {
        if (this.userToken == null) {
            throw new IllegalStateException("[Client Service] No user is logged in or the session token is missing.");
        }
    
        // Define the user's role (based on the context, set it accordingly)
        Auth.ROLE role = Auth.ROLE.REGULATORY_AUTHORITY;  // Assuming the role is 'FEMTECH_USER'
    
        // Build the GetDataAccessLogRequest message
        
        
        Data.GetDataAccessLogRequest getDataAccessLogRequest = Data.GetDataAccessLogRequest.newBuilder()
                .setUsername(username)
                .setRole(role)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setToken(this.userToken)
                .build();
    
        System.out.println("[Client Service] Fetching data access logs for user: " + username);
    
        // Send the request to the server and get the response
        
        Data.GetDataAccessLogResponse getDataAccessLogResponse = this.stub.getDataAccessLog(getDataAccessLogRequest);
    
        // Check for any error message in the response
        if (getDataAccessLogResponse.hasErrorMessage()) {
            throw new Exception(getDataAccessLogResponse.getErrorMessage().getDescription());
        }
    
        // Process and print the data access logs
        List<Data.DataAccessLog> dataAccessLogs = getDataAccessLogResponse.getDatAccessLogList();
    
        // Check if there are any logs to display
        if (dataAccessLogs.isEmpty()) {
            System.out.println("[Client Service] No data access logs found for user: " + username);
            return;
        }
        
        System.out.println("=== Data Access Logs for user: " + username + " ===");
        
        // Print each data access log entry
        for (Data.DataAccessLog log : dataAccessLogs) {
            System.out.println("Date: " + formatConsentDate(log.getDate()));
            System.out.println("Purpose: " + log.getPurpose());
            System.out.println("Data Types: " + log.getDataTypeList());  // Print the list of data types
            System.out.println("Entity: " + log.getEntity());
            System.out.println("Status: " + log.getStatus());
            System.out.println("--------------------------");
        }
    }
    
    

    @Override
    public void close() {
        this.channel.shutdown();
    }
}