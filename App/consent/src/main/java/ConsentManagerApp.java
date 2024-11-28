import pbfProto.ConsentManager;
import pbfProto.Auth;
import models.*;
import pbfProto.Auth;
import services.ConsentManagerService;
import services.RabbitMqService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.apache.http.auth.AUTH;

public class ConsentManagerApp {

    public static void main(String[] args) {
        try {
            final String SERVICE_ID = "service_consent_manager";
            final int NUMBER_OF_THREADS = 1000;

            Map<String, String> idsCache = new TreeMap<>();

            RabbitMqService rabbitMqService = new RabbitMqService();
            ConsentManagerService consentManagerService = new ConsentManagerService(rabbitMqService);

            System.out.println("[Consent Manager App] Initializing server...");

            RpcServer consentManagerServer = rabbitMqService.newRpcServer(SERVICE_ID);
            Channel channel = consentManagerServer.getChannel();

            ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

            // Handler for setting consent
            consentManagerServer.addOperationHandler(Operations.SET_CONSENT, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    ConsentManager.SetConsentRequest request = ConsentManager.SetConsentRequest.parseFrom(delivery.getBody());
                    //Auth.ROLE role = consentManagerService.authorizeUserRole(request.getToken());
                    
                    String [] authorization = consentManagerService.authorizeUser(request.getToken());
                    Auth.ROLE role = Auth.ROLE.valueOf(authorization[0]);
                    String id = authorization[1];

                    ConsentManager.SetConsentResponse.Builder responseBuilder = ConsentManager.SetConsentResponse.newBuilder();

                    if (role.equals(Auth.ROLE.FEMTECH_USER) && id != null) {
                        //System.out.println("passei a verificação");
                        // Set the consent using the service
                        //System.out.println("vou fazer set no service");
                        Consent consent = consentManagerService.setConsent(
                            id,
                            new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()),
                            request.getPrivacySettingsList().stream()
                                .map(entry -> new ConsentEntry(
                                    DataType.valueOf(entry.getDataType().name()),
                                    Purpose.valueOf(entry.getPurpose().name()),
                                    entry.getConsentGiven()))
                                .collect(Collectors.toList())
                        );
                        //System.out.println("esta aqui o consent:" + consent);

                        if (consent == null) {
                            // If setting consent fails, add an error message
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Consent Manager Service] Failed to set consent for user.")
                                .build());
                        }

                        // If consent is successfully set, do not return the consent matrix in the response
                    } else {
                        // Handle unauthorized role
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                            .setDescription("[Consent Manager Service] User is not allowed to set consent.")
                            .build());
                    }

                    // Send response and acknowledge the delivery
                    consentManagerServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });


            // Handler for receiving and processing the Data Types Authorization Request
            consentManagerServer.addOperationHandler(Operations.DATA_TYPES_AUTHORIZATION_REQUEST, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                     //System.out.println("entrei no handler do verificar consent para os datatypes");
                    // Step 1: Parse the incoming request
                    ConsentManager.GetAuthorizedDataTypesRequest request = ConsentManager.GetAuthorizedDataTypesRequest.parseFrom(delivery.getBody());

                    // Step 2: Create a response builder for GetAuthorizedDataTypesResponse
                    ConsentManager.GetAuthorizedDataTypesResponse.Builder responseBuilder = ConsentManager.GetAuthorizedDataTypesResponse.newBuilder();

                    try {
                        // Step 3: Retrieve the user ID and purpose from the request
                        String userId = request.getUserId();
                        //System.out.println("recebi o pedido para esse user:" + userId);
                        ConsentManager.Purpose protoPurpose = request.getPurpose();
                        //System.out.println("recebi o pedido para esse purpose:" + protoPurpose);

                        //System.out.println("[Consent Manager] Processing authorization request for user: " + userId + ", purpose: " + protoPurpose.name());

                        // Step 4: Convert ConsentManager.Purpose to Local Purpose Enum
                        Purpose localPurpose = Purpose.valueOf(protoPurpose.name());

                        // Step 5: Call the verifyConsent method using the converted Purpose
                        List<DataType> authorizedDataTypes = consentManagerService.verifyConsent(userId, localPurpose);

                        // Step 6: Convert the local DataType list back to ConsentManager.DataType list
                        if (authorizedDataTypes != null && !authorizedDataTypes.isEmpty()) {
                            // Map local DataType to ConsentManager.DataType and explicitly convert to ArrayList
                            List<ConsentManager.DataType> protoDataTypes = new ArrayList<>(authorizedDataTypes.stream()
                                    .map(localDataType -> ConsentManager.DataType.valueOf(localDataType.name()))
                                    .collect(Collectors.toList()));

                            // Step 7: Build the response with the converted proto DataType list
                            responseBuilder.addAllDataTypes(protoDataTypes);
                        } else {
                            // If no data types are authorized, set an error message
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                    .setDescription("[Consent Manager Service] No authorized data types found for user: " + userId));
                        }

                    } catch (Exception e) {
                        // Step 8: Error handling
                        System.err.println("[Consent Manager] Error processing data types authorization request: " + e.getMessage());
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Consent Manager Service] Error processing request: " + e.getMessage()));
                    }

                    // Step 9: Send the response back and acknowledge the delivery
                    consentManagerServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });

            // Handler for getting consent
            consentManagerServer.addOperationHandler(Operations.GET_CONSENT, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    // Parse the incoming request
                    ConsentManager.GetConsentRequest request = ConsentManager.GetConsentRequest.parseFrom(delivery.getBody());
                    //System.out.println("entrei no get consent handler com esse token:" + request.getToken());
                    // Build response
                    ConsentManager.GetConsentResponse.Builder responseBuilder = ConsentManager.GetConsentResponse.newBuilder();

                    // Authorize the user's role
                    //System.out.println("vou autorizar o role");
                    //Auth.ROLE role = consentManagerService.authorizeUserRole(request.getToken());
                    Auth.ROLE role = Auth.ROLE.FEMTECH_USER;

                    if (role.equals(Auth.ROLE.FEMTECH_USER)) {

                        //System.out.println("vou buscar o consent");
                        // Fetch the consent from the ConsentManagerService
                        Consent consent = consentManagerService.getConsent(request.getUserId());

                        // If consent is found, build the response
                        if (consent != null) {
                            responseBuilder.setConsentMatrix(
                                ConsentManager.Consent.newBuilder()
                                    .setDate(consent.getDate())
                                    .addAllPrivacySettings(
                                        consent.getPrivacySettings().stream()
                                            .map(entry -> ConsentManager.ConsentEntry.newBuilder()
                                                .setDataType(ConsentManager.DataType.valueOf(entry.getDataType().name()))
                                                .setPurpose(ConsentManager.Purpose.valueOf(entry.getPurpose().name()))
                                                .setConsentGiven(entry.isConsentGiven())
                                                .build())
                                            .collect(Collectors.toList())
                                    )
                            );
                        } else {
                            // If no consent is found, return an error message
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Consent Manager Service] No consent found for user: " + request.getUserId())
                                .build());
                        }

                    } else {
                        // Handle unauthorized role
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                            .setDescription("[Consent Manager Service] User is not authorized to get consent for user: " + request.getUserId())
                            .build());
                    }

                    // Send the response back to the requester and acknowledge the delivery
                    consentManagerServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });



            // Handler for getting consent history
            consentManagerServer.addOperationHandler(Operations.GET_CONSENT_HISTORY, new Operation() {
                @Override
                public void execute(String consumerTag, Delivery delivery) throws IOException {
                    // Parse the incoming request
                    ConsentManager.GetConsentHistoryRequest request = ConsentManager.GetConsentHistoryRequest.parseFrom(delivery.getBody());

                    // Build the response
                    ConsentManager.GetConsentHistoryResponse.Builder responseBuilder = ConsentManager.GetConsentHistoryResponse.newBuilder();

                    // Authorize the user's role
                    //Auth.ROLE role = consentManagerService.authorizeUserRole(request.getToken());
                    Auth.ROLE role = Auth.ROLE.FEMTECH_USER;

                    if (role.equals(Auth.ROLE.FEMTECH_USER) || role.equals(Auth.ROLE.REGULATORY_AUTHORITY)) {

                        // Fetch the consent history from the ConsentManagerService
                        List<Consent> consentHistory = consentManagerService.getConsentHistory(request.getUserId());

                        // If consent history is found, build the response
                        if (consentHistory != null && !consentHistory.isEmpty()) {
                            responseBuilder.addAllConsentVersions(
                                consentHistory.stream()
                                    .map(consent -> ConsentManager.Consent.newBuilder()
                                        .setDate(consent.getDate())
                                        .addAllPrivacySettings(
                                            consent.getPrivacySettings().stream()
                                                .map(entry -> ConsentManager.ConsentEntry.newBuilder()
                                                    .setDataType(ConsentManager.DataType.valueOf(entry.getDataType().name()))
                                                    .setPurpose(ConsentManager.Purpose.valueOf(entry.getPurpose().name()))
                                                    .setConsentGiven(entry.isConsentGiven())
                                                    .build())
                                                .collect(Collectors.toList())
                                        )
                                        .build()
                                    ).collect(Collectors.toList())
                            );
                        } else {
                            // If no history is found, return an error message
                            responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                                .setDescription("[Consent Manager Service] No consent history found for user: " + request.getUserId())
                                .build());
                        }

                    } else {
                        // Handle unauthorized role
                        responseBuilder.setErrorMessage(Auth.ErrorMessage.newBuilder()
                            .setDescription("[Consent Manager Service] User is not authorized to get consent history for user: " + request.getUserId())
                            .build());
                    }

                    // Send the response back to the requester and acknowledge the delivery
                    consentManagerServer.sendResponseAndAck(delivery, responseBuilder.build().toByteArray());
                }
            });

            DeliverCallback mainHandler = (consumerTag, delivery) -> {
                executorService.submit(() -> {
                    try {
                        System.out.println("[Consent Manager App] Received new operation request!");
                        consentManagerServer.executeOperationHandler(delivery);
                    } catch (IOException e) {
                        System.err.println("[Consent Manager App] Unexpected error occurred: " + e.getMessage());
                    }
                });
            };

            channel.basicConsume(SERVICE_ID, false, mainHandler, (consumerTag -> {}));
        } catch (Exception e) {
            System.err.println("[Consent Manager App] Unexpected error occurred: " + e.getMessage());
        }
    }
}
