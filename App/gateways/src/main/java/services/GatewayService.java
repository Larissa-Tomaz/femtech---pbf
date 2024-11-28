package services;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.RpcClientParams;
import pbfProto.Auth;
import pbfProto.Data;
import pbfProto.ConsentManager;
import pbfProto.GatewaysGrpc;
import io.grpc.stub.StreamObserver;
import models.Operations;
import models.RpcClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class GatewayService extends GatewaysGrpc.GatewaysImplBase {

    private final RabbitMqService rabbitMqService;
    private final static String SERVICE_ID = "service_gateways";

    public GatewayService(RabbitMqService rabbitMqService) {
        this.rabbitMqService = rabbitMqService;
    }

    private RpcClient getRpcClient() throws IOException, TimeoutException {
        Channel channel = this.rabbitMqService.createNewChannel();
        return new RpcClient(new RpcClientParams().channel(channel));
    }

    // Register user
    @Override
    public void registerUser(Auth.UserRegistrationRequest request,
                             StreamObserver<Auth.UserRegistrationResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending registration request for user: " + request.getUsername());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.NEW_USER_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();
            responseObserver.onNext(Auth.UserRegistrationResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while registering a user: " + e.getMessage());
        }
    }
    

    // Authenticate user
    @Override
    public void authenticateUser(Auth.UserAuthenticationRequest request,
                                 StreamObserver<Auth.UserAuthenticationResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending authentication request for user: " + request.getUsername());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.AUTHENTICATION_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Auth.UserAuthenticationResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while authenticating a user: " + e.getMessage());
        }
    }

    @Override
    public void authorizeUserRole(Auth.UserRoleAuthorizationRequest request,
                                StreamObserver<Auth.UserRoleAuthorizationResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending role authorization request for user with token: " + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.ROLE_AUTHORIZATION_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Auth.UserRoleAuthorizationResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while authorizing user role: " + e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void authorizeUserId(Auth.UserIdAuthorizationRequest request,
                                StreamObserver<Auth.UserIdAuthorizationResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending ID authorization request for user with token: " + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.ID_AUTHORIZATION_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Auth.UserIdAuthorizationResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while authorizing user ID: " + e.getMessage());
            responseObserver.onError(e);
        }
    }

    // Submit user data
    @Override
    public void submitData(Data.SubmitDataRequest request,
                            StreamObserver<Data.SubmitDataResponse> responseObserver) {
        String serviceQueueName = "service_data";
        try {
            System.out.println("[Gateway Service] Sending data submission request for user with token: "
                    + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.SUBMIT_USER_DATA,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Data.SubmitDataResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while submitting data logs for a user: "
                    + e.getMessage());
        }
    }

    @Override
    public void getDataAccessLog(Data.GetDataAccessLogRequest request,
                            StreamObserver<Data.GetDataAccessLogResponse> responseObserver) {
        String serviceQueueName = "service_data";
        try {
            System.out.println("[Gateway Service] Sending fetch data access request with token: "
                    + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.GET_DATA_ACCESS_LOGS,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Data.GetDataAccessLogResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while fetching data access logs for a user: "
                    + e.getMessage());
        }
    }
   
    @Override
    public void getUserData(Data.GetDataRequest request,
                            StreamObserver<Data.GetDataResponse> responseObserver) {
        String serviceQueueName = "service_data";
        try {
            System.out.println("[Gateway Service] Sending fetch data request for user: " + request.getUsername());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.GET_USER_DATA, 
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Data.GetDataResponse.parseFrom(response)); // Use correct response type
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while fetching data logs for a user: "
                    + e.getMessage());
        }
    }


    // Get consent
    @Override
    public void getConsent(ConsentManager.GetConsentRequest request,
                           StreamObserver<ConsentManager.GetConsentResponse> responseObserver) {
        String serviceQueueName = "service_consent_manager";
        try {
            System.out.println("[Gateway Service] Sending getConsent request for user: " + request.getUserId());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.GET_CONSENT,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(ConsentManager.GetConsentResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while fetching consent for a user: "
                    + e.getMessage());
        }
    }

    @Override
    public void setConsent(ConsentManager.SetConsentRequest request,
                           StreamObserver<ConsentManager.SetConsentResponse> responseObserver) {
        String serviceQueueName = "service_consent_manager";
        try {
            System.out.println("[Gateway Service] Sending setConsent request with token:" + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.SET_CONSENT,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(ConsentManager.SetConsentResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while fetching consent for a user: "
                    + e.getMessage());
        }
    }

    // Get consent history
    @Override
    public void getConsentHistory(ConsentManager.GetConsentHistoryRequest request,
                                  StreamObserver<ConsentManager.GetConsentHistoryResponse> responseObserver) {
        String serviceQueueName = "service_consent_manager";
        try {
            System.out.println("[Gateway Service] Sending consent history request for user: " + request.getUserId());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.GET_CONSENT_HISTORY,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(ConsentManager.GetConsentHistoryResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while fetching consent history for a user: "
                    + e.getMessage());
        }
    }

    // Get data access logs
    /*@Override
    public void getDataAccessLogs(Data.GetDataAccessLogRequest request,
                                  StreamObserver<Data.GetDataAccessLogResponse> responseObserver) {
        String serviceQueueName = "service_data";
        try {
            System.out.println("[Gateway Service] Sending data access log request for user: " + request.getUsername());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.GET_DATA_ACCESS_LOGS,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Data.GetDataAccessLogResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while fetching data access logs for a user: "
                    + e.getMessage());
        }
    }*/

    @Override
    public void logoutUser(Auth.UserLogoutRequest request,
                                 StreamObserver<Auth.UserLogoutResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending logout request with token: " + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.LOGOUT_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Auth.UserLogoutResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while logging out a user: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(Auth.UserDeleteRequest request,
                                 StreamObserver<Auth.UserDeleteResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending user delete request for user: " + request.getToken());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.DELETE_USER_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Auth.UserDeleteResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while deleting a user: " + e.getMessage());
        }
    }

    @Override
    public void deleteUserData(Data.DeleteDataRequest request,
                                 StreamObserver<Data.DeleteDataResponse> responseObserver) {
        String serviceQueueName = "service_registry";
        try {
            System.out.println("[Gateway Service] Sending user delete request for user: " + request.getUsername());
            RpcClient rpcClient = this.getRpcClient();
            final byte[] response = rpcClient.sendRequest(
                    serviceQueueName,
                    rpcClient.getChannel(),
                    Operations.DELETE_USER_REQUEST,
                    request.toByteArray()
            );
            rpcClient.close();

            responseObserver.onNext(Data.DeleteDataResponse.parseFrom(response));
            responseObserver.onCompleted();
        } catch (IOException | TimeoutException | ExecutionException | InterruptedException e) {
            System.err.println("[Gateway Service] An error occurred while deleting a user: " + e.getMessage());
        }
    }

}
