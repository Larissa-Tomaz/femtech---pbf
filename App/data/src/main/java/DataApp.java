import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import pbfProto.Auth;
import pbfProto.ConsentManager;
import pbfProto.Data;
import models.*;
import services.DataService;
import services.RabbitMqService;
import java.util.Arrays;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DataApp {
    public static void main(String[] args) {
        try {
            final String SERVICE_ID = "service_data";
            final int NUMBER_OF_THREADS = 1000;

            RabbitMqService rabbitMqService = new RabbitMqService();
            DataService dataService = new DataService(rabbitMqService);

            Map<String, UserRole> rolesCache = new TreeMap<>();

            System.out.println("[Data App] Initializing server...");

            RpcServer dataServer = rabbitMqService.newRpcServer(SERVICE_ID);
            Channel channel = dataServer.getChannel();

            ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

            // Handler for submitting fertility data logs
            dataServer.addOperationHandler(Operations.SUBMIT_USER_DATA, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    Data.SubmitDataRequest request = Data.SubmitDataRequest.parseFrom(delivery.getBody());

                    String [] authorization = dataService.authorizeUser(request.getToken());
                    Auth.ROLE role = Auth.ROLE.valueOf(authorization[0]);
                    String id = authorization[1];

                    Data.SubmitDataResponse.Builder responseBuilder = Data.SubmitDataResponse.newBuilder();

                    if (role.equals(Auth.ROLE.FEMTECH_USER) && id != null) {
                        //System.out.println("vou fazer o request para o service");
                        Data.FertilityDailyLog fertilityLog = request.getFertilityDailyLog();

                        List<Float> temperatureList = fertilityLog.getTemperatureList();
                        List<Integer> bpmList = fertilityLog.getBpmList();
                        String bleeding = fertilityLog.getBleeding();
                        String sexualActivity = fertilityLog.getSexualActivity();
                        String date = fertilityLog.getDate();

                        boolean submissionStatus = dataService.submitUserFertilityData(
                                id, date, temperatureList, bpmList, bleeding, sexualActivity);

                        if (submissionStatus) {
                            System.out.println("[Data Service] Successfully submitted fertility daily log for user: " + id);
                        } else {
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Data Service] Failed to submit fertility daily log for user: " + id)
                                    .build());
                        }
                    } else {
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Data Service] User is not authorized to submit fertility logs.")
                                .build());
                    }

                    dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });


            dataServer.addOperationHandler(Operations.GET_DATA_ACCESS_LOGS, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    //System.out.println("entrei no handler");
                    Data.GetDataAccessLogRequest request;
                    Data.GetDataAccessLogResponse.Builder responseBuilder = Data.GetDataAccessLogResponse.newBuilder();
            
                    try {
                        // Parse the request from the delivery
                        request = Data.GetDataAccessLogRequest.parseFrom(delivery.getBody());
                    } catch (Exception e) {
                        System.err.println("[Data Service] Error parsing request: " + e.getMessage());
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("Invalid request format."));
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                        return;
                    }
            
                    try {
                        // Authorize the user's role and user ID from the token
                        String [] authorization = dataService.authorizeUser(request.getToken());
                        Auth.ROLE role = Auth.ROLE.valueOf(authorization[0]);
                        String authorizedUserId = authorization[1];

                        //System.out.println("autorizei");
                     
                        if (role == null ) {
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Data Service] User is not authorized to access this data."));
                                    
                        } else {
                            // Fetch the data access logs for the specified username and date range
                            //System.out.println("vou fazer fetch dos logs");
                            List<DataAccessLog> accessLogs = dataService.getDataAccessLogs(
                                    request.getUsername(), request.getStartDate(), request.getEndDate());
            
                            if (accessLogs != null && !accessLogs.isEmpty()) {
                                // Build the response with the retrieved access logs
                                List<Data.DataAccessLog> dataAccessLogs = accessLogs.stream()
                                        .map(log -> Data.DataAccessLog.newBuilder()
                                                
                                                .setDate(log.getDate())
                                                .setPurpose(log.getPurpose())
                                                .addAllDataType(log.getDataTypes().stream()
                                                        .map(dataTypeStr -> {
                                                            try {
                                                                return ConsentManager.DataType.valueOf(dataTypeStr.toUpperCase());
                                                            } catch (IllegalArgumentException e) {
                                                                System.err.println("[Data Service] Invalid data type: " + dataTypeStr);
                                                                return ConsentManager.DataType.UNRECOGNIZED;
                                                            }
                                                        })
                                                        .collect(Collectors.toList()))
                                                .setEntity(log.getEntity())
                                                .setRole(log.getRole())  // Use the role from the model class
                                                .setStatus(log.getStatus())
                                                .build())
                                        .collect(Collectors.toList());
            
                                responseBuilder.addAllDatAccessLog(dataAccessLogs);
                                System.out.println("[Data Service] Fetched " + dataAccessLogs.size() + " data access logs for user: " + request.getUsername());
                            } else {
                                // No logs found
                                responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                        .setDescription("[Data Service] No data access logs found for user: " + request.getUsername()));
                            }
                        }
            
                        // Send the response back to the client
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
            
                    } catch (Exception e) {
                        System.err.println("[Data Service] Error fetching data access logs: " + e.getMessage());
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Data Service] Error fetching data access logs: " + e.getMessage()));
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            });
            
            

            dataServer.addOperationHandler(Operations.GET_USER_DATA, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    //System.out.println("entrei no handler");
                    Data.GetDataRequest request;
                    Data.GetDataResponse.Builder responseBuilder = Data.GetDataResponse.newBuilder();
            
                    try {
                        // Parse the request from delivery
                        //System.out.println("vou fazer parse do request");
                        request = Data.GetDataRequest.parseFrom(delivery.getBody());
                    } catch (Exception e) {
                        //System.out.println("deu erro parsing o request");
                        System.err.println("[Data Service] Error parsing request: " + e.getMessage());
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("Invalid request format."));
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                        return;
                    }
            
                    String accessStatus = "denied";
                    List<ConsentManager.DataType> canAccess = null;
                    ConsentManager.Purpose purpose = request.getPurpose();  // Use the correct type for purpose

                    try {
                        // Authorize the user's role and user ID from the token
                        String [] authorization = dataService.authorizeUser(request.getToken());
                        Auth.ROLE requesterRole = Auth.ROLE.valueOf(authorization[0]);
                        String requesterUserId = authorization[1];


                        if (requesterRole == Auth.ROLE.FEMTECH_USER && request.getUsername().equals(requesterUserId)) {
                            // If role is FEMTECH_USER and username matches requesterUserId, give access to all data types
                            canAccess = Arrays.asList(ConsentManager.DataType.ACCOUNT_DATA,
                                                    ConsentManager.DataType.FERTILITY_DATA,
                                                    ConsentManager.DataType.PROFILE_DATA);
                            //System.out.println("esta eh a lista de datatypes:" + canAccess);
                            accessStatus = "accepted";
                        } else {
                            
                                
                                // Verify consent based on the purpose
                                //System.out.println("Purpose: " + purpose.name());
                                canAccess = dataService.verifyConsent(request.getUsername(), purpose);  // Use correct type
                                //System.out.println("List of DataTypes: " + canAccess);

                                if (canAccess == null || canAccess.isEmpty()) { // Handle the null or empty list case safely
                                    responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                        .setDescription("[Data Service] User is not authorized to access this data."));
                                } else {
                                    accessStatus = "accepted";
                                }
                            
                        }

                        // Log the data access attempt
                        dataService.logDataAccess(
                                request.getUsername(),
                                java.time.LocalDateTime.now().toString(),
                                purpose.name(),  // Convert purpose to string for logging
                                canAccess != null
                                ? canAccess.stream()
                                           .map(dataTypeInstance -> dataTypeInstance.toString()) // Or replace with a specific method or field accessor
                                           .collect(Collectors.toList())
                                : List.of(),
                                request.getEntity(),
                                requesterRole,
                                accessStatus
                        );

                        if ("accepted".equals(accessStatus)) {
                            // Fetch the user data from the DataService if access was granted
                            StoredData storedData = dataService.getStoredDataForUser(request.getUsername(), canAccess);

                            if (storedData != null) {
                                Data.StoredData.Builder storedDataBuilder = Data.StoredData.newBuilder();
                            
                                if (storedData.getAccountData() != null) {
                                    storedDataBuilder.setAccountData(
                                        Auth.UserAccountData.newBuilder()
                                            .setProfileName(storedData.getAccountData().getProfileName() == null ? "" : storedData.getAccountData().getProfileName())
                                            .setRegistrationDate(storedData.getAccountData().getRegistrationDate() == null ? "" : storedData.getAccountData().getRegistrationDate())
                                            .setPaymentPlan(storedData.getAccountData().getPaymentPlan() == null ? "" : storedData.getAccountData().getPaymentPlan())
                                            .setSubscriptionStatus(storedData.getAccountData().getSubscriptionStatus() != null && storedData.getAccountData().getSubscriptionStatus())
                                            .build()
                                    );
                                }
                            
                                if (storedData.getProfileData() != null) {
                                    storedDataBuilder.setProfileData(
                                        Auth.UserProfileData.newBuilder()
                                            .setAge(storedData.getProfileData().getAge() == null ? "" : storedData.getProfileData().getAge())
                                            .setMenstrualCycleLength(storedData.getProfileData().getMenstrualCycleLength() == null ? "" : storedData.getProfileData().getMenstrualCycleLength())
                                            .setPeriodLength(storedData.getProfileData().getPeriodLength() == null ? "" : storedData.getProfileData().getPeriodLength())
                                            .setMainContraceptiveMethod(storedData.getProfileData().getMainContraceptiveMethod() == null ? "" : storedData.getProfileData().getMainContraceptiveMethod())
                                            .build()
                                    );
                                }
                            
                                if (storedData.getFertilityDailyLogs() != null) {
                                    storedDataBuilder.addAllFertilityDailyLog(
                                        storedData.getFertilityDailyLogs().stream()
                                            .map(fertilityLog -> Data.FertilityDailyLog.newBuilder()
                                                .setDate(fertilityLog.getDate() == null ? "" : fertilityLog.getDate())
                                                .addAllTemperature(fertilityLog.getTemperature() != null ? fertilityLog.getTemperature() : List.of())
                                                .addAllBpm(fertilityLog.getBpm() != null ? fertilityLog.getBpm() : List.of())
                                                .setBleeding(fertilityLog.getBleeding() == null ? "" : fertilityLog.getBleeding())
                                                .setSexualActivity(fertilityLog.getSexualActivity() == null ? "" : fertilityLog.getSexualActivity())
                                                .build()
                                            ).collect(Collectors.toList())
                                    );
                                }
                            
                                responseBuilder.setStoredData(storedDataBuilder.build());
                            } else {
                                responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Data Service] No data found for user: " + request.getUsername()));
                            }
                        }

                        // Send the response back to the client
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());

                    } catch (Exception e) {
                        System.err.println("[Data Service] Consent verification error: " + e.getMessage());
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Data Service] Consent verification failed: " + e.getMessage()));
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            });

            // Handler for deleting user data
            dataServer.addOperationHandler(Operations.DELETE_USER_DATA, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    Data.DeleteDataRequest request = Data.DeleteDataRequest.parseFrom(delivery.getBody());

                    Auth.ROLE role = dataService.authorizeUserRole(request.getToken());
                    Data.DeleteDataResponse.Builder responseBuilder = Data.DeleteDataResponse.newBuilder();

                    try {
                        // Check if the user is authorized
                        if (role.equals(Auth.ROLE.FEMTECH_USER) && request.getUsername().equals(dataService.authorizeUserId(request.getToken()))) {
                            // Call data service to delete user data
                            dataService.deleteUserData(request.getUsername());

                            System.out.println("[Data Service] Data deleted for user: " + request.getUsername());
                        } else {
                            // If the user is not authorized to delete logs
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Data Service] User is not authorized to delete data for user: " + request.getUsername())
                                    .build());
                        }
                    } catch (Exception e) {
                        System.err.println("[Data App] Error deleting user data: " + e.getMessage());
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("An error occurred while deleting data for user: " + e.getMessage())
                                .build());
                    } finally {
                        // Send the response to the client whether success or failure
                        dataServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            });

            DeliverCallback mainHandler = (consumerTag, delivery) -> {
                executorService.submit(() -> {
                    try {
                        System.out.println("[Data App] Received new operation request!");
                        dataServer.executeOperationHandler(delivery);
                    } catch (IOException e) {
                        System.err.println("[Data App] Unexpected error occurred: " + e.getMessage());
                    }
                });
            };

            channel.basicConsume(SERVICE_ID, false, mainHandler, (consumerTag -> {}));
        } catch (Exception e) {
            System.err.println("[Data App] Unexpected error occurred: " + e.getMessage());
        }
    }
}

                                                       
