import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import pbfProto.ConsentManager;
import services.ClientService;

public class ClientApp {
    // Define all the command strings
    private static final String SIGN_IN_COMMAND = "login";
    private static final String LOGOUT_USER_COMMAND = "logout";
    private static final String SIGN_UP_COMMAND = "registerUser";
    private static final String DELETE_ACCOUNT_COMMAND = "deleteUser";
    private static final String DATA_SUBMIT_COMMAND = "submitData";
    private static final String GET_DATA_COMMAND = "fetchData";
    private static final String DELETE_DATA_COMMAND = "deleteUserData";
    private static final String GET_DATA_ACCESS_LOG_COMMAND = "getAccessLogs";
    private static final String GET_CONSENT_COMMAND = "getConsent";
    private static final String SUBMIT_CONSENT_COMMAND = "setConsent";
    private static final String CONSENT_HISTORY_COMMAND = "getConsentHistory";

    public static void main(String[] args) {
        System.out.println("Client Initializing...");
        if (args.length < 2) {
            System.err.println("Argument(s) missing! Expected at least: <host> <port>");
            return;
        }

        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        String role = "FEMTECH_USER";
        ClientService clientService = new ClientService(host, port);
        Scanner scanner = new Scanner(System.in);
        String command = "";

        try {
            while (true) {
                System.out.print("Enter command (or type 'exit' to quit): ");
                command = scanner.nextLine().trim();

                // Exit condition
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                // Split the command into arguments by spaces
                String[] cmdArgs = command.split("\\s+");

                // Check which command was entered
                if (cmdArgs.length > 0) {
                    switch (cmdArgs[0]) {
                        case SIGN_UP_COMMAND:
                            if (cmdArgs.length < 11) {
                                System.err.println("Argument(s) missing! Expected: <registerUser> <username> <profile_name> <payment_plan> <subscription_status> <password> <role> <age> <menstrualCycleLength> <periodLength> <mainContraceptiveMethod>");
                                continue;
                            }
                            
                            // Extract arguments for the registration request
                            String username = cmdArgs[1];
                            String profileName = cmdArgs[2];
                            String paymentPlan = cmdArgs[3];
                            Boolean subscriptionStatus = Boolean.parseBoolean(cmdArgs[4]);
                            String password = cmdArgs[5];
                            role = cmdArgs[6];
                            
                            // Extract additional profile data arguments
                            String age = cmdArgs[7];
                            String menstrualCycleLength = cmdArgs[8];
                            String periodLength = cmdArgs[9];
                            String mainContraceptiveMethod = cmdArgs[10];

                            // Call the registerUser method from ClientService with both account and profile data
                            try {
                                clientService.registerUser(username, profileName, paymentPlan, subscriptionStatus, password, role, age, menstrualCycleLength, periodLength, mainContraceptiveMethod);
                                System.out.println("User registered successfully.");
                            } catch (Exception e) {
                                System.err.println("Error registering user: " + e.getMessage());
                            }
                            break;

                        case SIGN_IN_COMMAND:
                            if (cmdArgs.length < 3) {
                                System.err.println("Argument(s) missing! Expected: <login> <username> <password>");
                                continue;
                            }
                            String loginUsername = cmdArgs[1];
                            String loginPassword = cmdArgs[2];
                            try {
                                clientService.loginUser(loginUsername, loginPassword);
                                System.out.println("User logged in successfully.");
                            } catch (Exception e) {
                                System.err.println("Error logging in: " + e.getMessage());
                            }
                            break;

                        case LOGOUT_USER_COMMAND:
                            try {
                                clientService.logoutUser();
                            } catch (Exception e) {
                                System.err.println("Error logging out: " + e.getMessage());
                            }
                            break;

                        case DATA_SUBMIT_COMMAND:
                            if (cmdArgs.length < 6) {
                                System.err.println("Argument(s) missing! Expected: <submitFertilityDailyLog> <temperatures> <bpmValues> <bleeding> <sexualActivity> <date>");
                                continue;
                            }

                            try {
                                // Parse the temperature values from the command arguments
                                List<Float> temperatures = Arrays.stream(cmdArgs[1].split(","))
                                                                .map(Float::parseFloat)
                                                                .collect(Collectors.toList());

                                // Parse the bpm values from the command arguments
                                List<Integer> bpmValues = Arrays.stream(cmdArgs[2].split(","))
                                                                .map(Integer::parseInt)
                                                                .collect(Collectors.toList());

                                // Extract the bleeding and sexual activity information
                                String bleeding = cmdArgs[3];
                                String sexualActivity = cmdArgs[4];

                                // Extract the date of the fertility daily log
                                String date = cmdArgs[5];

                                // Call the submitFertilityDailyLog method from ClientService
                                clientService.submitFertilityDailyLog(temperatures, bpmValues, bleeding, sexualActivity, date);
                                System.out.println("Fertility daily log submitted successfully.");
                            } catch (Exception e) {
                                System.err.println("Error submitting fertility daily log: " + e.getMessage());
                            }
                            break;

                        case GET_DATA_COMMAND:
                            if (cmdArgs.length < 4) {
                                System.err.println("Argument(s) missing! Expected: <fetchData> <username> <purpose> <entity>");
                                continue;
                            }
                        
                            try {
                                String fetchUsername = cmdArgs[1];
                                String fetchPurpose = cmdArgs[2];
                                String fetchEntity = cmdArgs[3];
                        
                                // Call the getUserData method from ClientService
                                clientService.getUserData(fetchUsername, fetchPurpose, fetchEntity);
                                System.out.println("User data fetched successfully.");
                            } catch (Exception e) {
                                System.err.println("Error fetching user data: " + e.getMessage());
                            }
                            break;
                        
                        

                        case DELETE_ACCOUNT_COMMAND:
                            if (cmdArgs.length < 1) {
                                System.err.println("Argument(s) missing! Expected: <deleteUser>");
                                continue;
                            }
                            try {
                                clientService.deleteUser();
                                System.out.println("User deleted successfully.");
                            } catch (Exception e) {
                                System.err.println("Error deleting user: " + e.getMessage());
                            }
                            break;

                        case DELETE_DATA_COMMAND:
                            if (cmdArgs.length < 2) {
                                System.err.println("Argument(s) missing! Expected: <deleteUserData> <username>");
                                continue;
                            }
                            try {
                                clientService.deleteUserData(cmdArgs[1]);
                                System.out.println("User data deleted successfully.");
                            } catch (Exception e) {
                                System.err.println("Error deleting user data: " + e.getMessage());
                            }
                            break;

                        case GET_DATA_ACCESS_LOG_COMMAND:  // <-- New case for access logs
                            if (cmdArgs.length < 2) {
                                System.err.println("Argument(s) missing! Expected: <getAccessLogs> <username>");
                                continue;
                            }
                            try {
                                // Parse the command arguments
                                String logUsername = cmdArgs[1];
                                String startDate = "20241103_000000";
                                String endDate = "20241106_000000";

                                // Call the getDataAccessLogs method
                                clientService.getDataAccessLogs(logUsername, startDate, endDate);
                                System.out.println("Data access logs fetched successfully.");
                            } catch (Exception e) {
                                System.err.println("Error fetching data access logs: " + e.getMessage());
                            }
                            break;

                        case GET_CONSENT_COMMAND:
                            if (cmdArgs.length < 2) {
                                System.err.println("Argument(s) missing! Expected: <getConsent> <username>");
                                continue;
                            }
                            try {
                                clientService.getConsent(cmdArgs[1], role);
                                System.out.println("Consent fetched successfully.");
                            } catch (Exception e) {
                                System.err.println("Error fetching consent: " + e.getMessage());
                            }
                            break;

                        case SUBMIT_CONSENT_COMMAND:
                            if (cmdArgs.length < 2) {
                                System.err.println("Argument(s) missing! Expected: <setConsent> <dataType:purpose:consentGiven>");
                                continue;
                            }
                        
                            // Initialize the map for consent selections
                            Map<String, Map<String, Boolean>> consentSelections = new HashMap<>();
                        
                            // Parse each consent entry from the input arguments
                            for (int i = 1; i < cmdArgs.length; i++) {
                                String[] parts = cmdArgs[i].split(":");
                        
                                // Ensure the correct format with exactly 3 parts: <dataType:purpose:consentGiven>
                                if (parts.length != 3) {
                                    System.err.println("Invalid format. Expected <dataType:purpose:consentGiven>");
                                    continue;
                                }
                        
                                // Extract and normalize the strings
                                String dataTypeStr = parts[0].toUpperCase();
                                String purposeStr = parts[1].toUpperCase();
                                boolean consentGiven;
                        
                                try {
                                    consentGiven = Boolean.parseBoolean(parts[2]);
                                } catch (Exception e) {
                                    System.err.println("Invalid consent value: " + parts[2] + ". Expected true/false.");
                                    continue;
                                }
                        
                                // Add to consentSelections map
                                consentSelections
                                    .computeIfAbsent(dataTypeStr, k -> new HashMap<>())
                                    .put(purposeStr, consentGiven);
                            }
                        
                            // Call the setConsent method using the constructed map
                            try {
                                clientService.setConsent(consentSelections);
                                System.out.println("Consent matrix set successfully.");
                            } catch (Exception e) {
                                System.err.println("Error setting consent matrix: " + e.getMessage());
                            }
                            break;

                        
                        case CONSENT_HISTORY_COMMAND:
                            if (cmdArgs.length < 2) {
                                System.err.println("Argument(s) missing! Expected: <getConsentHistory> <username>");
                                continue;
                            }
                            try {
                                clientService.getConsentHistory(cmdArgs[1], role);
                                System.out.println("Consent history fetched successfully.");
                            } catch (Exception e) {
                                System.err.println("Error fetching consent history: " + e.getMessage());
                            }
                            break;

                        default:
                            System.err.println("Unknown command: " + cmdArgs[0]);
                            break;
                    }
                } else {
                    System.err.println("No command entered.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while executing command: " + e.getMessage());
        } finally {
            clientService.close();
            scanner.close();
        }
    }
}
