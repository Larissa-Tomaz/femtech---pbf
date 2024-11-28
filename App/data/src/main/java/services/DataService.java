package services;

import models.*;

import org.hyperledger.fabric.gateway.ContractException;
import pbfProto.Auth;
import pbfProto.ConsentManager;
import pbfProto.ConsentManager.DataType;
import com.owlike.genson.Genson;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.RpcClientParams;

import services.HyperledgerService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DataService {
    private RabbitMqService rabbitMqService;
    private HyperledgerService hyperledgerService;
    private int logCounter = 0; 

    public DataService(RabbitMqService rabbitMqService) throws Exception {
        this.rabbitMqService = rabbitMqService;
        this.hyperledgerService = new HyperledgerService();
    }

    public boolean submitUserFertilityData(String username, String date, List<Float> temperature, List<Integer> bpm,
                                       String bleeding, String sexualActivity) {
        System.out.println("[Data Management Service] Submitting fertility data log for user: " + username);                           
        //System.out.println("entrei no service");
        try {
            // Step 1: Validate and collect valid temperature values
            List<Float> validatedTemperatures = validateTemperatures(temperature);

            // Step 2: Validate and collect valid bpm values
            List<Integer> validatedBpmValues = validateBpmValues(bpm);

            // Step 3: Create a FertilityData object using the validated values, including the date
            FertilityData fertilityData = new FertilityData(
                    date,  // Include the date field
                    validatedTemperatures.isEmpty() ? null : validatedTemperatures,
                    validatedBpmValues.isEmpty() ? null : validatedBpmValues,
                    bleeding == null || bleeding.isEmpty() ? null : bleeding,
                    sexualActivity == null || sexualActivity.isEmpty() ? null : sexualActivity
            );
          
            this.hyperledgerService.submitUserData(fertilityData, username);
            

            //System.out.println("[Data Service] Successfully submitted fertility daily log for user: " + username);
            return true;
        } catch (Exception e) {
            System.err.println("[Data Service] Failed to submit fertility daily log: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to validate temperature values
    private List<Float> validateTemperatures(List<Float> temperatures) {
        List<Float> validTemperatures = new ArrayList<>();
        if (temperatures != null) {
            for (Float tempValue : temperatures) {
                if (tempValue >= 35.0 && tempValue <= 42.0) {
                    validTemperatures.add(tempValue);
                } else {
                    System.out.println("[Data Service] Temperature value out of range (35.0 - 42.0). Skipping value: " + tempValue);
                }
            }
        }
        return validTemperatures;
    }

    // Helper method to validate bpm values
    private List<Integer> validateBpmValues(List<Integer> bpmValues) {
        List<Integer> validBpmValues = new ArrayList<>();
        if (bpmValues != null) {
            for (Integer bpmValue : bpmValues) {
                if (bpmValue > 0) {
                    validBpmValues.add(bpmValue);
                } else {
                    System.out.println("[Data Service] BPM value must be positive. Skipping value: " + bpmValue);
                }
            }
        }
        return validBpmValues;
    }

    public void logDataAccess(String username, String date, String purpose, List<String> dataTypes, String entity, Auth.ROLE role, String status) {
        try {

            String logId = Integer.toString(logCounter);

            logCounter = logCounter + 1;

            // Create a DataAccessLog object
            DataAccessLog accessLog = new DataAccessLog(
                    logId,             // logId
                    date,              // date
                    purpose,           // purpose
                    dataTypes,         // dataTypes as List<String>
                    entity,            // entity
                    role,              // role
                    status             // status (accepted/denied)
            );


            //System.out.println("eu vou escrever para o data access log");
            // Call the Hyperledger service to write the access log
            this.hyperledgerService.writeToDataAccessLog(username, accessLog);

            System.out.println("[Data Service] Successfully logged data access for user: " + username);
        } catch (IOException | ContractException | InterruptedException | TimeoutException e) {
            System.err.println("[Data Service] Failed to log data access for user: " + username + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //delete data from db and write to ledger that data was deleted
    public void deleteUserData(String username) {
        try {
            // Call Hyperledger service to delete the user's data
            this.hyperledgerService.deleteUserData(username);

            System.out.println("[Data Service] Data deleted successfully for user: " + username);
            return;
        } catch (Exception e) {
            System.err.println("[Data Service] Error occurred while deleting data for user: " + e.getMessage());
            return;
        }
    }

    public List<DataAccessLog> getDataAccessLogs(String username, String startDate, String endDate) {
        System.out.println("[Data Management Service] Getting data access logs for user: " + username);
        //System.out.println("entrei no service");
        try {
            // Call Hyperledger service to fetch the access logs for the user
            List<DataAccessLog> accessLogs = hyperledgerService.getDataAccessLogs(username, startDate, endDate);

            if (accessLogs != null && !accessLogs.isEmpty()) {
                System.out.println("[Data Service] Access logs fetched successfully for user: " + username);
                return accessLogs;
            } else {
                System.out.println("[Data Service] No access logs found for user: " + username);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("[Data Service] Error fetching access logs for user: " + e.getMessage());
            return null;
        }
    }

    public Auth.ROLE authorizeUserRole(String token) {
        try {
            final String registryQueueName = "service_registry";
            Channel channel = this.rabbitMqService.createNewChannel();
            RpcClient rpcClient = new RpcClient(new RpcClientParams().channel(channel));

            Auth.UserRoleAuthorizationRequest authorizationRequest = Auth.UserRoleAuthorizationRequest.newBuilder()
                    .setToken(token)
                    .build();

            final byte[] response = rpcClient.sendRequest(
                    registryQueueName,
                    channel,
                    Operations.ROLE_AUTHORIZATION_REQUEST,
                    authorizationRequest.toByteArray()
            );
            rpcClient.close();

            Auth.UserRoleAuthorizationResponse authorizationResponse = Auth.UserRoleAuthorizationResponse.parseFrom(response);
            if (authorizationResponse.hasErrorMessage()) {
                throw new Exception(authorizationResponse.getErrorMessage().getDescription());
            }

            return Auth.ROLE.valueOf(authorizationResponse.getRole().getValueDescriptor().getName());
        } catch (Exception e) {
            System.err.println("[Data Service] Failed to authorize user with token \"" + token + "\": "
                    + e.getMessage());
            return null;
        }
    }

    public String[] authorizeUser(String token) {
        //System.out.println("entrei no authorize user");
        try {
            final String registryQueueName = "service_registry";
            Channel channel = this.rabbitMqService.createNewChannel();
            RpcClient rpcClient = new RpcClient(new RpcClientParams().channel(channel));

            Auth.UserAuthorizationRequest authorizationRequest = Auth.UserAuthorizationRequest.newBuilder()
                    .setToken(token)
                    .build();

            final byte[] response = rpcClient.sendRequest(
                    registryQueueName,
                    channel,
                    Operations.AUTHORIZATION_REQUEST,
                    authorizationRequest.toByteArray()
            );
            rpcClient.close();

            Auth.UserAuthorizationResponse authorizationResponse = Auth.UserAuthorizationResponse.parseFrom(response);
            if (authorizationResponse.hasErrorMessage()) {
                throw new Exception(authorizationResponse.getErrorMessage().getDescription());
            }

            return new String[] { authorizationResponse.getRole().toString(), authorizationResponse.getUsername() };
        } catch (Exception e) {
            System.err.println("[Consent Manager Service] Failed to authorize user with token \"" + token + "\": "
                    + e.getMessage());
            return null;
        }
    }

    public StoredData getStoredDataForUser(String username, List<DataType> authorizedDataTypes) {
        System.out.println("[Data Management Service] Getting data from user: " + username);
        try {
    
            // Step 2: Initialize fields to null, to be filled if permission is granted
            AccountData accountData = null;
            ProfileData profileData = null;
            List<FertilityData> fertilityDataLogs = new ArrayList<>();

            final String registryQueueName = "service_registry";
            Channel channel = this.rabbitMqService.createNewChannel();
            RpcClient rpcClient = new RpcClient(new RpcClientParams().channel(channel));
    
            // Step 3: Check each authorized data type and retrieve accordingly
            if (authorizedDataTypes.contains(DataType.ACCOUNT_DATA)) {

                Auth.GetAccountDataRequest getAccountDataRequest = Auth.GetAccountDataRequest.newBuilder()
                    .setUsername(username)
                    .build();

                final byte[] response = rpcClient.sendRequest(
                        registryQueueName,
                        channel,
                        Operations.GET_ACCOUNT_DATA,
                        getAccountDataRequest.toByteArray()
                );
                rpcClient.close();

                Auth.GetAccountDataResponse getAccountDataResponse = Auth.GetAccountDataResponse.parseFrom(response);
                if (getAccountDataResponse.hasErrorMessage()) {
                    throw new Exception(getAccountDataResponse.getErrorMessage().getDescription());
                }


                accountData = new AccountData(
                    getAccountDataResponse.getAccountData().getProfileName(),
                    getAccountDataResponse.getAccountData().getRegistrationDate(),
                    getAccountDataResponse.getAccountData().getPaymentPlan(),
                    getAccountDataResponse.getAccountData().getSubscriptionStatus()       

                );
                
            }
    
            if (authorizedDataTypes.contains(DataType.PROFILE_DATA)) {
                Auth.GetProfileDataRequest getProfileDataRequest = Auth.GetProfileDataRequest.newBuilder()
                .setUsername(username)
                .build();

                final byte[] response = rpcClient.sendRequest(
                        registryQueueName,
                        channel,
                        Operations.GET_PROFILE_DATA,
                        getProfileDataRequest.toByteArray()
                );
                rpcClient.close();

                Auth.GetProfileDataResponse getProfileDataResponse = Auth.GetProfileDataResponse.parseFrom(response);
                if (getProfileDataResponse.hasErrorMessage()) {
                    throw new Exception(getProfileDataResponse.getErrorMessage().getDescription());
                }

                profileData = new ProfileData(
                    getProfileDataResponse.getProfileData().getAge(),
                    getProfileDataResponse.getProfileData().getMenstrualCycleLength(),
                    getProfileDataResponse.getProfileData().getPeriodLength(),
                    getProfileDataResponse.getProfileData().getMainContraceptiveMethod()       

                );

            }
    
            if (authorizedDataTypes.contains(DataType.FERTILITY_DATA)) {
                fertilityDataLogs = this.hyperledgerService.getFertilityLogs(username);
            }
    
            // Step 4: Create the StoredData object based on the authorized data types
            StoredData storedData = new StoredData(accountData, profileData, fertilityDataLogs);
    
            System.out.println("[Data Service] Successfully retrieved stored data for user: " + username);
            return storedData;
    
        } catch (Exception e) {
            System.err.println("[Data Service] Failed to retrieve stored data for user: " + username + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    


    public List<ConsentManager.DataType> verifyConsent(String username, ConsentManager.Purpose purpose) {
        
        try {
            final String registryQueueName = "service_consent_manager";
            Channel channel = this.rabbitMqService.createNewChannel();
            RpcClient rpcClient = new RpcClient(new RpcClientParams().channel(channel));
    
            // Build the request with the correct method calls for setting fields
            ConsentManager.GetAuthorizedDataTypesRequest dataTypesRequest = ConsentManager.GetAuthorizedDataTypesRequest.newBuilder()
                .setUserId(username)
                .setPurpose(purpose)
                .build();
            
                //System.out.println("vou enviar request para o consent microservice");
            // Send the request to the Consent Manager service and get the response
            final byte[] response = rpcClient.sendRequest(
                    registryQueueName,
                    channel,
                    Operations.DATA_TYPES_AUTHORIZATION_REQUEST,
                    dataTypesRequest.toByteArray()
            );
            rpcClient.close();
    
            //System.out.println("recebi uma response do consent microservice" + response);
            // Parse the response from the RPC call
            ConsentManager.GetAuthorizedDataTypesResponse dataTypesResponse = ConsentManager.GetAuthorizedDataTypesResponse.parseFrom(response);
            if (dataTypesResponse.hasErrorMessage()) {
                throw new Exception(dataTypesResponse.getErrorMessage().getDescription());
            }
    
            // Extract the authorized data types and return them
            return new ArrayList<>(dataTypesResponse.getDataTypesList());
    
        } catch (Exception e) {
            System.err.println("[Data Service] Failed to authorize user : " + e.getMessage());
            return null;
        }
    }
    

    public String authorizeUserId(String token) {
        try {
            final String registryQueueName = "service_registry";
            Channel channel = this.rabbitMqService.createNewChannel();
            RpcClient rpcClient = new RpcClient(new RpcClientParams().channel(channel));

            Auth.UserIdAuthorizationRequest authorizationRequest = Auth.UserIdAuthorizationRequest.newBuilder()
                    .setToken(token)
                    .build();

            final byte[] response = rpcClient.sendRequest(
                    registryQueueName,
                    channel,
                    Operations.ID_AUTHORIZATION_REQUEST,
                    authorizationRequest.toByteArray()
            );
            rpcClient.close();

            Auth.UserIdAuthorizationResponse authorizationResponse = Auth.UserIdAuthorizationResponse.parseFrom(response);
            if (authorizationResponse.hasErrorMessage()) {
                throw new Exception(authorizationResponse.getErrorMessage().getDescription());
            }

            return authorizationResponse.getId();
        } catch (Exception e) {
            System.err.println("[Data Service] Failed to authorize user with token \"" + token + "\": "
                    + e.getMessage());
            return null;
        }
    }


}
