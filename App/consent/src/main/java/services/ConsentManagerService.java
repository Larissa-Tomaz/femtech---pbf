package services;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.RpcClientParams;
import models.*;
import org.hyperledger.fabric.gateway.ContractException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import pbfProto.Auth;

public class ConsentManagerService {
    private RabbitMqService rabbitMqService;
    private final HyperledgerService hyperledgerService;
    private final static String SERVICE_ID = "service_consent_manager";

    public ConsentManagerService(RabbitMqService rabbitMqService) throws Exception {
        this.rabbitMqService = rabbitMqService;
        this.hyperledgerService = new HyperledgerService();
        //System.out.println(this.hyperledgerService);
    }

    public Consent setConsent(String userId, String date, List<ConsentEntry> privacySettings) {
        //System.out.println("entrei no consent service");
        // Validate input data: date and privacy settings should not be empty
        System.out.println("[Consent Manager Service] Setting Consent for user: " + userId);
        if (privacySettings == null || privacySettings.isEmpty() || userId == null || userId.isEmpty() || date == null || date.isEmpty()) {
            System.out.println("[Consent Manager Service] Failed to register consent. Invalid data provided for user: " + userId);
            //System.out.println("vou retornar null pq teve invalid data");
            return null;
        }
    
        try {
            // Register the consent on Hyperledger or another storage system
            //System.out.println("vou guardar no hyperledger");
            Consent consent = this.hyperledgerService.setConsent(userId, date, privacySettings);
            //ystem.out.println("este é o consent que o hyperledger retornou: " + consent);
            return consent;
        } catch (IOException | ContractException | InterruptedException | TimeoutException e) {
            System.err.println("[Consent Manager Service] Failed to register consent for user: " + userId + ". Error: " + e.getMessage());
            return null;
        }
    }
    
    

    public Consent getConsent(String userId) {
        System.out.println("[Consent Manager Service] Fetching Consent for user: " + userId);
        try {
            return this.hyperledgerService.getConsent(userId);
        } catch (IOException | ContractException | InterruptedException | TimeoutException e) {
            System.err.println("[Consent Manager Service] Failed to get consent: " + e.getMessage());
            return null;
        }
    }

    public List<Consent> getConsentHistory(String userId) {
        System.out.println("[Consent Manager Service] Fetching Consent History for user: " + userId);
        try {
            return this.hyperledgerService.getConsentHistory(userId);
        } catch (IOException | ContractException | InterruptedException | TimeoutException e) {
            System.err.println("[Consent Manager Service] Failed to register consent: " + e.getMessage());
            return null;
        }
    }

    public Auth.ROLE authorizeUserRole(String token) {
        //System.out.println("entrei no authorize user role");
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
            System.err.println("[Consent Manager Service] Failed to authorize user with token \"" + token + "\": "
                    + e.getMessage());
            return null;
        }
    }

    public List<DataType> verifyConsent(String userId, Purpose purpose) {
    // Fetch the user's consent settings from the repository or in-memory cache
        //System.out.println("entrei no verify consent do consent service");
        try {
            return this.hyperledgerService.verifyConsent(userId, purpose);
        } catch (IOException | ContractException | InterruptedException | TimeoutException e) {
            System.err.println("[Consent Manager Service] Failed to register consent: " + e.getMessage());
            return null;
        }
    }


    public String authorizeUserId(String token) {
        //System.out.println("entrei no authorize user id");
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
            System.err.println("[Consent Manager Service] Failed to authorize user with token \"" + token + "\": "
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

}