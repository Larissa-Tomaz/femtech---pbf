import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import pbfProto.Auth;
import models.*;
import services.RabbitMqService;
import services.RegistryService;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistryApp {
    public static void main(String[] args) {
        try {
            final String SERVICE_ID = "service_registry";
            final int NUMBER_OF_THREADS = 200;
            RabbitMqService rabbitMqService = new RabbitMqService();
            RegistryService registryService = new RegistryService();
            registryService.loadHyperledgerService();

            System.out.println("[Registry App] Initializing server...");
            RpcServer registryServer = rabbitMqService.newRpcServer(SERVICE_ID);
            Channel channel = registryServer.getChannel();

            ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

            
            registryServer.addOperationHandler(Operations.NEW_USER_REQUEST, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    
                    Auth.UserRegistrationRequest request = Auth.UserRegistrationRequest.parseFrom(delivery.getBody());
            
                    User user = registryService.registerUser(
                            request.getAccountData().getProfileName(),
                            request.getAccountData().getRegistrationDate(),
                            request.getAccountData().getPaymentPlan(),
                            request.getAccountData().getSubscriptionStatus(),
                            request.getRole(),
                            request.getUsername(),
                            request.getPassword(),
                            request.getProfileData().getAge(),
                            request.getProfileData().getMenstrualCycleLength(),
                            request.getProfileData().getPeriodLength(),
                            request.getProfileData().getMainContraceptiveMethod()
                    );
                    
                    Auth.UserRegistrationResponse.Builder responseBuilder = Auth.UserRegistrationResponse.newBuilder();
            
                    if (user == null) {
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                            .setDescription("[Registry Service] Failed to register user " + request.getUsername())
                            .build());
                    } else {

                        
                        responseBuilder.setUser(Auth.User.newBuilder()
                                .setRole(Auth.ROLE.valueOf(user.getRole().toString()))
                                .setRegistrationDate(user.getAccountData().getRegistrationDate())
                                .setProfileName(user.getAccountData().getProfileName())
                                .setUsername(user.getUsername())
                                .build());
            
                        System.out.println("[Registry Service] Registered user " + user.getUsername() + " successfully!");
                    }
            
                    registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });


            registryServer.addOperationHandler(Operations.GET_ACCOUNT_DATA, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    
                    Auth.GetAccountDataRequest request = Auth.GetAccountDataRequest.parseFrom(delivery.getBody());

                    UserAccountData accountData = registryService.getAccountData(request.getUsername());

                    Auth.GetAccountDataResponse.Builder responseBuilder = Auth.GetAccountDataResponse.newBuilder();

                    if (accountData == null) {
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                            .setDescription("[Registry Service] Failed to get accountData for user " + request.getUsername())
                            .build());
                    } else {
                        responseBuilder.setAccountData(Auth.UserAccountData.newBuilder()
                                .setRegistrationDate(accountData.getRegistrationDate())
                                .setProfileName(accountData.getProfileName())
                                .setPaymentPlan(accountData.getPaymentPlan())
                                .setSubscriptionStatus(accountData.getSubscriptionStatus())
                                .build());

                    }
                    registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });

            registryServer.addOperationHandler(Operations.GET_PROFILE_DATA, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    
                    Auth.GetProfileDataRequest request = Auth.GetProfileDataRequest.parseFrom(delivery.getBody());

                    UserProfileData profileData = registryService.getProfileData(request.getUsername());

                    Auth.GetProfileDataResponse.Builder responseBuilder = Auth.GetProfileDataResponse.newBuilder();

                    if (profileData == null) {
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                            .setDescription("[Registry Service] Failed to get profileData for user " + request.getUsername())
                            .build());
                    } else {
                        responseBuilder.setProfileData(Auth.UserProfileData.newBuilder()
                                .setAge(profileData.getAge())
                                .setMainContraceptiveMethod(profileData.getMainContraceptiveMethod())
                                .setPeriodLength(profileData.getPeriodLength())
                                .setMenstrualCycleLength(profileData.getMenstrualCycleLength())
                                .build());

                    }
                    registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });

            

            registryServer.addOperationHandler(Operations.LOGOUT_REQUEST, new Operation() {
                    @Override
                    public void execute(String consumerTag, Delivery delivery) throws IOException {
                        Auth.UserLogoutRequest request = Auth.UserLogoutRequest
                                .parseFrom(delivery.getBody());

                        boolean success = registryService.logoutUser(
                                request.getToken()
                        );

                        Auth.UserLogoutResponse.Builder responseBuilder = Auth.UserLogoutResponse.newBuilder();

                        if (!success) {
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Registry Service] Failed to logout user with token: " + request.getToken())
                                    .build());
                        } else {
                            System.out.println("[Registry Service] Logged out user with token " + request.getToken()
                                    + " successfully.");
                        }

                        registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            );

            registryServer.addOperationHandler(Operations.AUTHENTICATION_REQUEST, new Operation() {
                    @Override
                    public void execute(String consumerTag, Delivery delivery) throws IOException {
                        Auth.UserAuthenticationRequest request = Auth.UserAuthenticationRequest
                                .parseFrom(delivery.getBody());
                        String token = registryService.authenticateUser(
                                request.getUsername(),
                                request.getPassword()
                        );

                        Auth.UserAuthenticationResponse.Builder responseBuilder = Auth.UserAuthenticationResponse.newBuilder();
                        if (token == null) {
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Registry Service] Failed to login user " + request.getUsername())
                                    .build());
                        } else {
                            User user = registryService.getUserByToken(token);
                            responseBuilder.setToken(token);

                            System.out.println("[Registry Service] Authenticated user " + user.getUsername() +
                                    " successfully.");
                        }

                        registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            );

            registryServer.addOperationHandler(Operations.DELETE_USER_REQUEST, new Operation() {
                    @Override
                    public void execute(String consumerTag, Delivery delivery) throws IOException {
                        Auth.UserDeleteRequest request = Auth.UserDeleteRequest
                                .parseFrom(delivery.getBody());

                        boolean success = registryService.deleteUser(
                                request.getToken()
                        );

                        Auth.UserDeleteResponse.Builder responseBuilder = Auth.UserDeleteResponse.newBuilder();

                        if (!success) {
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Registry Service] Failed to delete user")
                                    .build());
                        } else {
                            System.out.println("[Registry Service] Deleted use successfully.");
                        }

                        registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            );

            registryServer.addOperationHandler(Operations.AUTHORIZATION_REQUEST, new Operation() {
                    @Override
                    public void execute(String consumerTag, Delivery delivery) throws IOException {
                        Auth.UserAuthorizationRequest request = Auth.UserAuthorizationRequest
                                .parseFrom(delivery.getBody());

                        User user = registryService.getUserByToken(request.getToken());


                        Auth.UserAuthorizationResponse.Builder responseBuilder = Auth.UserAuthorizationResponse.newBuilder();

                        if (user.getRole().toString() == null) {
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Registry Service] Failed to authorize user with token: " + request.getToken())
                                    .build());
                        } else {
                            responseBuilder.setRole(Auth.ROLE.valueOf(user.getRole().toString()))
                                            .setUsername(user.getUsername());

                            System.out.println("[Registry Service] Authorized user " + request.getToken() +
                                    " successfully.");
                        }

                        registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                    }
                }
            );
            

            /*registryServer.addOperationHandler(Operations.ROLE_AUTHORIZATION_REQUEST, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    Auth.UserRoleAuthorizationRequest request = Auth.UserRoleAuthorizationRequest
                            .parseFrom(delivery.getBody());

                    Auth.ROLE role = registryService.authorizeUserRole(
                            request.getToken()
                    );

                    Auth.UserRoleAuthorizationResponse.Builder responseBuilder = Auth.UserRoleAuthorizationResponse.newBuilder();

                    if (role == null) {
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Registry Service] Failed to authorize user with token: " + request.getToken())
                                .build());
                    } else {
                        responseBuilder.setRole(Auth.ROLE.valueOf(role.toString()));
                        System.out.println("[Registry Service] Authorized user " + request.getToken() +
                                " successfully.");
                    }

                    registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });

            registryServer.addOperationHandler(Operations.ID_AUTHORIZATION_REQUEST, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    Auth.UserIdAuthorizationRequest request = Auth.UserIdAuthorizationRequest
                            .parseFrom(delivery.getBody());

                    String id = registryService.authorizeUserId(
                            request.getToken()
                    );

                    Auth.UserIdAuthorizationResponse.Builder responseBuilder = Auth.UserIdAuthorizationResponse.newBuilder();

                    if (id == null) {
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Registry Service] Failed to authorize user with token: " + request.getToken())
                                .build());
                    } else {
                        responseBuilder.setId(id);
                        System.out.println("[Registry Service] Authorized user " + request.getToken() +
                                " successfully.");
                    }

                    registryServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });*/



            DeliverCallback mainHandler = (consumerTag, delivery) -> {
                executorService.submit(() -> {
                    try {
                        System.out.println("[Registry App] Received new operation request!");
                        registryServer.executeOperationHandler(delivery);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("[Registry App] Unexpected error occurred: " + e.getMessage());
                    }
                });
            };

            channel.basicConsume(SERVICE_ID, false, mainHandler, (consumerTag -> {}));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.err.println("[Registry App] Unexpected error occurred: " + e.getMessage());
        }

    }
}