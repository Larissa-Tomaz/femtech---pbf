package pbfProto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.41.0)",
    comments = "Source: gateways.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GatewaysGrpc {

  private GatewaysGrpc() {}

  public static final String SERVICE_NAME = "pbfProto.Gateways";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pbfProto.Auth.UserRegistrationRequest,
      pbfProto.Auth.UserRegistrationResponse> getRegisterUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterUser",
      requestType = pbfProto.Auth.UserRegistrationRequest.class,
      responseType = pbfProto.Auth.UserRegistrationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Auth.UserRegistrationRequest,
      pbfProto.Auth.UserRegistrationResponse> getRegisterUserMethod() {
    io.grpc.MethodDescriptor<pbfProto.Auth.UserRegistrationRequest, pbfProto.Auth.UserRegistrationResponse> getRegisterUserMethod;
    if ((getRegisterUserMethod = GatewaysGrpc.getRegisterUserMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getRegisterUserMethod = GatewaysGrpc.getRegisterUserMethod) == null) {
          GatewaysGrpc.getRegisterUserMethod = getRegisterUserMethod =
              io.grpc.MethodDescriptor.<pbfProto.Auth.UserRegistrationRequest, pbfProto.Auth.UserRegistrationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserRegistrationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserRegistrationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("RegisterUser"))
              .build();
        }
      }
    }
    return getRegisterUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Auth.UserAuthenticationRequest,
      pbfProto.Auth.UserAuthenticationResponse> getAuthenticateUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthenticateUser",
      requestType = pbfProto.Auth.UserAuthenticationRequest.class,
      responseType = pbfProto.Auth.UserAuthenticationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Auth.UserAuthenticationRequest,
      pbfProto.Auth.UserAuthenticationResponse> getAuthenticateUserMethod() {
    io.grpc.MethodDescriptor<pbfProto.Auth.UserAuthenticationRequest, pbfProto.Auth.UserAuthenticationResponse> getAuthenticateUserMethod;
    if ((getAuthenticateUserMethod = GatewaysGrpc.getAuthenticateUserMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getAuthenticateUserMethod = GatewaysGrpc.getAuthenticateUserMethod) == null) {
          GatewaysGrpc.getAuthenticateUserMethod = getAuthenticateUserMethod =
              io.grpc.MethodDescriptor.<pbfProto.Auth.UserAuthenticationRequest, pbfProto.Auth.UserAuthenticationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthenticateUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserAuthenticationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserAuthenticationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("AuthenticateUser"))
              .build();
        }
      }
    }
    return getAuthenticateUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Auth.UserRoleAuthorizationRequest,
      pbfProto.Auth.UserRoleAuthorizationResponse> getAuthorizeUserRoleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthorizeUserRole",
      requestType = pbfProto.Auth.UserRoleAuthorizationRequest.class,
      responseType = pbfProto.Auth.UserRoleAuthorizationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Auth.UserRoleAuthorizationRequest,
      pbfProto.Auth.UserRoleAuthorizationResponse> getAuthorizeUserRoleMethod() {
    io.grpc.MethodDescriptor<pbfProto.Auth.UserRoleAuthorizationRequest, pbfProto.Auth.UserRoleAuthorizationResponse> getAuthorizeUserRoleMethod;
    if ((getAuthorizeUserRoleMethod = GatewaysGrpc.getAuthorizeUserRoleMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getAuthorizeUserRoleMethod = GatewaysGrpc.getAuthorizeUserRoleMethod) == null) {
          GatewaysGrpc.getAuthorizeUserRoleMethod = getAuthorizeUserRoleMethod =
              io.grpc.MethodDescriptor.<pbfProto.Auth.UserRoleAuthorizationRequest, pbfProto.Auth.UserRoleAuthorizationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthorizeUserRole"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserRoleAuthorizationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserRoleAuthorizationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("AuthorizeUserRole"))
              .build();
        }
      }
    }
    return getAuthorizeUserRoleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Auth.UserIdAuthorizationRequest,
      pbfProto.Auth.UserIdAuthorizationResponse> getAuthorizeUserIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthorizeUserId",
      requestType = pbfProto.Auth.UserIdAuthorizationRequest.class,
      responseType = pbfProto.Auth.UserIdAuthorizationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Auth.UserIdAuthorizationRequest,
      pbfProto.Auth.UserIdAuthorizationResponse> getAuthorizeUserIdMethod() {
    io.grpc.MethodDescriptor<pbfProto.Auth.UserIdAuthorizationRequest, pbfProto.Auth.UserIdAuthorizationResponse> getAuthorizeUserIdMethod;
    if ((getAuthorizeUserIdMethod = GatewaysGrpc.getAuthorizeUserIdMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getAuthorizeUserIdMethod = GatewaysGrpc.getAuthorizeUserIdMethod) == null) {
          GatewaysGrpc.getAuthorizeUserIdMethod = getAuthorizeUserIdMethod =
              io.grpc.MethodDescriptor.<pbfProto.Auth.UserIdAuthorizationRequest, pbfProto.Auth.UserIdAuthorizationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthorizeUserId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserIdAuthorizationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserIdAuthorizationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("AuthorizeUserId"))
              .build();
        }
      }
    }
    return getAuthorizeUserIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Auth.UserLogoutRequest,
      pbfProto.Auth.UserLogoutResponse> getLogoutUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LogoutUser",
      requestType = pbfProto.Auth.UserLogoutRequest.class,
      responseType = pbfProto.Auth.UserLogoutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Auth.UserLogoutRequest,
      pbfProto.Auth.UserLogoutResponse> getLogoutUserMethod() {
    io.grpc.MethodDescriptor<pbfProto.Auth.UserLogoutRequest, pbfProto.Auth.UserLogoutResponse> getLogoutUserMethod;
    if ((getLogoutUserMethod = GatewaysGrpc.getLogoutUserMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getLogoutUserMethod = GatewaysGrpc.getLogoutUserMethod) == null) {
          GatewaysGrpc.getLogoutUserMethod = getLogoutUserMethod =
              io.grpc.MethodDescriptor.<pbfProto.Auth.UserLogoutRequest, pbfProto.Auth.UserLogoutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LogoutUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserLogoutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserLogoutResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("LogoutUser"))
              .build();
        }
      }
    }
    return getLogoutUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Auth.UserDeleteRequest,
      pbfProto.Auth.UserDeleteResponse> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteUser",
      requestType = pbfProto.Auth.UserDeleteRequest.class,
      responseType = pbfProto.Auth.UserDeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Auth.UserDeleteRequest,
      pbfProto.Auth.UserDeleteResponse> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<pbfProto.Auth.UserDeleteRequest, pbfProto.Auth.UserDeleteResponse> getDeleteUserMethod;
    if ((getDeleteUserMethod = GatewaysGrpc.getDeleteUserMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getDeleteUserMethod = GatewaysGrpc.getDeleteUserMethod) == null) {
          GatewaysGrpc.getDeleteUserMethod = getDeleteUserMethod =
              io.grpc.MethodDescriptor.<pbfProto.Auth.UserDeleteRequest, pbfProto.Auth.UserDeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Auth.UserDeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("DeleteUser"))
              .build();
        }
      }
    }
    return getDeleteUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Data.GetDataRequest,
      pbfProto.Data.GetDataResponse> getGetUserDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserData",
      requestType = pbfProto.Data.GetDataRequest.class,
      responseType = pbfProto.Data.GetDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Data.GetDataRequest,
      pbfProto.Data.GetDataResponse> getGetUserDataMethod() {
    io.grpc.MethodDescriptor<pbfProto.Data.GetDataRequest, pbfProto.Data.GetDataResponse> getGetUserDataMethod;
    if ((getGetUserDataMethod = GatewaysGrpc.getGetUserDataMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getGetUserDataMethod = GatewaysGrpc.getGetUserDataMethod) == null) {
          GatewaysGrpc.getGetUserDataMethod = getGetUserDataMethod =
              io.grpc.MethodDescriptor.<pbfProto.Data.GetDataRequest, pbfProto.Data.GetDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.GetDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.GetDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("GetUserData"))
              .build();
        }
      }
    }
    return getGetUserDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Data.SubmitDataRequest,
      pbfProto.Data.SubmitDataResponse> getSubmitDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitData",
      requestType = pbfProto.Data.SubmitDataRequest.class,
      responseType = pbfProto.Data.SubmitDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Data.SubmitDataRequest,
      pbfProto.Data.SubmitDataResponse> getSubmitDataMethod() {
    io.grpc.MethodDescriptor<pbfProto.Data.SubmitDataRequest, pbfProto.Data.SubmitDataResponse> getSubmitDataMethod;
    if ((getSubmitDataMethod = GatewaysGrpc.getSubmitDataMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getSubmitDataMethod = GatewaysGrpc.getSubmitDataMethod) == null) {
          GatewaysGrpc.getSubmitDataMethod = getSubmitDataMethod =
              io.grpc.MethodDescriptor.<pbfProto.Data.SubmitDataRequest, pbfProto.Data.SubmitDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.SubmitDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.SubmitDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("SubmitData"))
              .build();
        }
      }
    }
    return getSubmitDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Data.DeleteDataRequest,
      pbfProto.Data.DeleteDataResponse> getDeleteUserDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteUserData",
      requestType = pbfProto.Data.DeleteDataRequest.class,
      responseType = pbfProto.Data.DeleteDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Data.DeleteDataRequest,
      pbfProto.Data.DeleteDataResponse> getDeleteUserDataMethod() {
    io.grpc.MethodDescriptor<pbfProto.Data.DeleteDataRequest, pbfProto.Data.DeleteDataResponse> getDeleteUserDataMethod;
    if ((getDeleteUserDataMethod = GatewaysGrpc.getDeleteUserDataMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getDeleteUserDataMethod = GatewaysGrpc.getDeleteUserDataMethod) == null) {
          GatewaysGrpc.getDeleteUserDataMethod = getDeleteUserDataMethod =
              io.grpc.MethodDescriptor.<pbfProto.Data.DeleteDataRequest, pbfProto.Data.DeleteDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteUserData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.DeleteDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.DeleteDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("DeleteUserData"))
              .build();
        }
      }
    }
    return getDeleteUserDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.Data.GetDataAccessLogRequest,
      pbfProto.Data.GetDataAccessLogResponse> getGetDataAccessLogMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDataAccessLog",
      requestType = pbfProto.Data.GetDataAccessLogRequest.class,
      responseType = pbfProto.Data.GetDataAccessLogResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.Data.GetDataAccessLogRequest,
      pbfProto.Data.GetDataAccessLogResponse> getGetDataAccessLogMethod() {
    io.grpc.MethodDescriptor<pbfProto.Data.GetDataAccessLogRequest, pbfProto.Data.GetDataAccessLogResponse> getGetDataAccessLogMethod;
    if ((getGetDataAccessLogMethod = GatewaysGrpc.getGetDataAccessLogMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getGetDataAccessLogMethod = GatewaysGrpc.getGetDataAccessLogMethod) == null) {
          GatewaysGrpc.getGetDataAccessLogMethod = getGetDataAccessLogMethod =
              io.grpc.MethodDescriptor.<pbfProto.Data.GetDataAccessLogRequest, pbfProto.Data.GetDataAccessLogResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDataAccessLog"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.GetDataAccessLogRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.Data.GetDataAccessLogResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("GetDataAccessLog"))
              .build();
        }
      }
    }
    return getGetDataAccessLogMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.ConsentManager.SetConsentRequest,
      pbfProto.ConsentManager.SetConsentResponse> getSetConsentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetConsent",
      requestType = pbfProto.ConsentManager.SetConsentRequest.class,
      responseType = pbfProto.ConsentManager.SetConsentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.ConsentManager.SetConsentRequest,
      pbfProto.ConsentManager.SetConsentResponse> getSetConsentMethod() {
    io.grpc.MethodDescriptor<pbfProto.ConsentManager.SetConsentRequest, pbfProto.ConsentManager.SetConsentResponse> getSetConsentMethod;
    if ((getSetConsentMethod = GatewaysGrpc.getSetConsentMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getSetConsentMethod = GatewaysGrpc.getSetConsentMethod) == null) {
          GatewaysGrpc.getSetConsentMethod = getSetConsentMethod =
              io.grpc.MethodDescriptor.<pbfProto.ConsentManager.SetConsentRequest, pbfProto.ConsentManager.SetConsentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetConsent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.SetConsentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.SetConsentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("SetConsent"))
              .build();
        }
      }
    }
    return getSetConsentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetConsentHistoryRequest,
      pbfProto.ConsentManager.GetConsentHistoryResponse> getGetConsentHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConsentHistory",
      requestType = pbfProto.ConsentManager.GetConsentHistoryRequest.class,
      responseType = pbfProto.ConsentManager.GetConsentHistoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetConsentHistoryRequest,
      pbfProto.ConsentManager.GetConsentHistoryResponse> getGetConsentHistoryMethod() {
    io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetConsentHistoryRequest, pbfProto.ConsentManager.GetConsentHistoryResponse> getGetConsentHistoryMethod;
    if ((getGetConsentHistoryMethod = GatewaysGrpc.getGetConsentHistoryMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getGetConsentHistoryMethod = GatewaysGrpc.getGetConsentHistoryMethod) == null) {
          GatewaysGrpc.getGetConsentHistoryMethod = getGetConsentHistoryMethod =
              io.grpc.MethodDescriptor.<pbfProto.ConsentManager.GetConsentHistoryRequest, pbfProto.ConsentManager.GetConsentHistoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConsentHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.GetConsentHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.GetConsentHistoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("GetConsentHistory"))
              .build();
        }
      }
    }
    return getGetConsentHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetConsentRequest,
      pbfProto.ConsentManager.GetConsentResponse> getGetConsentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConsent",
      requestType = pbfProto.ConsentManager.GetConsentRequest.class,
      responseType = pbfProto.ConsentManager.GetConsentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetConsentRequest,
      pbfProto.ConsentManager.GetConsentResponse> getGetConsentMethod() {
    io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetConsentRequest, pbfProto.ConsentManager.GetConsentResponse> getGetConsentMethod;
    if ((getGetConsentMethod = GatewaysGrpc.getGetConsentMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getGetConsentMethod = GatewaysGrpc.getGetConsentMethod) == null) {
          GatewaysGrpc.getGetConsentMethod = getGetConsentMethod =
              io.grpc.MethodDescriptor.<pbfProto.ConsentManager.GetConsentRequest, pbfProto.ConsentManager.GetConsentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConsent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.GetConsentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.GetConsentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("GetConsent"))
              .build();
        }
      }
    }
    return getGetConsentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetAuthorizedDataTypesRequest,
      pbfProto.ConsentManager.GetAuthorizedDataTypesResponse> getGetConsentedDataTypesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConsentedDataTypes",
      requestType = pbfProto.ConsentManager.GetAuthorizedDataTypesRequest.class,
      responseType = pbfProto.ConsentManager.GetAuthorizedDataTypesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetAuthorizedDataTypesRequest,
      pbfProto.ConsentManager.GetAuthorizedDataTypesResponse> getGetConsentedDataTypesMethod() {
    io.grpc.MethodDescriptor<pbfProto.ConsentManager.GetAuthorizedDataTypesRequest, pbfProto.ConsentManager.GetAuthorizedDataTypesResponse> getGetConsentedDataTypesMethod;
    if ((getGetConsentedDataTypesMethod = GatewaysGrpc.getGetConsentedDataTypesMethod) == null) {
      synchronized (GatewaysGrpc.class) {
        if ((getGetConsentedDataTypesMethod = GatewaysGrpc.getGetConsentedDataTypesMethod) == null) {
          GatewaysGrpc.getGetConsentedDataTypesMethod = getGetConsentedDataTypesMethod =
              io.grpc.MethodDescriptor.<pbfProto.ConsentManager.GetAuthorizedDataTypesRequest, pbfProto.ConsentManager.GetAuthorizedDataTypesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConsentedDataTypes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.GetAuthorizedDataTypesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pbfProto.ConsentManager.GetAuthorizedDataTypesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GatewaysMethodDescriptorSupplier("GetConsentedDataTypes"))
              .build();
        }
      }
    }
    return getGetConsentedDataTypesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GatewaysStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewaysStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewaysStub>() {
        @java.lang.Override
        public GatewaysStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewaysStub(channel, callOptions);
        }
      };
    return GatewaysStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GatewaysBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewaysBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewaysBlockingStub>() {
        @java.lang.Override
        public GatewaysBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewaysBlockingStub(channel, callOptions);
        }
      };
    return GatewaysBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GatewaysFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GatewaysFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GatewaysFutureStub>() {
        @java.lang.Override
        public GatewaysFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GatewaysFutureStub(channel, callOptions);
        }
      };
    return GatewaysFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GatewaysImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Registry Service
     * </pre>
     */
    public void registerUser(pbfProto.Auth.UserRegistrationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserRegistrationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterUserMethod(), responseObserver);
    }

    /**
     */
    public void authenticateUser(pbfProto.Auth.UserAuthenticationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserAuthenticationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthenticateUserMethod(), responseObserver);
    }

    /**
     */
    public void authorizeUserRole(pbfProto.Auth.UserRoleAuthorizationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserRoleAuthorizationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthorizeUserRoleMethod(), responseObserver);
    }

    /**
     */
    public void authorizeUserId(pbfProto.Auth.UserIdAuthorizationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserIdAuthorizationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthorizeUserIdMethod(), responseObserver);
    }

    /**
     */
    public void logoutUser(pbfProto.Auth.UserLogoutRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserLogoutResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLogoutUserMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(pbfProto.Auth.UserDeleteRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserDeleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    /**
     * <pre>
     * Data Service
     * </pre>
     */
    public void getUserData(pbfProto.Data.GetDataRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.GetDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserDataMethod(), responseObserver);
    }

    /**
     */
    public void submitData(pbfProto.Data.SubmitDataRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.SubmitDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitDataMethod(), responseObserver);
    }

    /**
     */
    public void deleteUserData(pbfProto.Data.DeleteDataRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.DeleteDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteUserDataMethod(), responseObserver);
    }

    /**
     */
    public void getDataAccessLog(pbfProto.Data.GetDataAccessLogRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.GetDataAccessLogResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDataAccessLogMethod(), responseObserver);
    }

    /**
     * <pre>
     * Consent Manager Service
     * </pre>
     */
    public void setConsent(pbfProto.ConsentManager.SetConsentRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.SetConsentResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetConsentMethod(), responseObserver);
    }

    /**
     */
    public void getConsentHistory(pbfProto.ConsentManager.GetConsentHistoryRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetConsentHistoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConsentHistoryMethod(), responseObserver);
    }

    /**
     */
    public void getConsent(pbfProto.ConsentManager.GetConsentRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetConsentResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConsentMethod(), responseObserver);
    }

    /**
     */
    public void getConsentedDataTypes(pbfProto.ConsentManager.GetAuthorizedDataTypesRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetAuthorizedDataTypesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConsentedDataTypesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Auth.UserRegistrationRequest,
                pbfProto.Auth.UserRegistrationResponse>(
                  this, METHODID_REGISTER_USER)))
          .addMethod(
            getAuthenticateUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Auth.UserAuthenticationRequest,
                pbfProto.Auth.UserAuthenticationResponse>(
                  this, METHODID_AUTHENTICATE_USER)))
          .addMethod(
            getAuthorizeUserRoleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Auth.UserRoleAuthorizationRequest,
                pbfProto.Auth.UserRoleAuthorizationResponse>(
                  this, METHODID_AUTHORIZE_USER_ROLE)))
          .addMethod(
            getAuthorizeUserIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Auth.UserIdAuthorizationRequest,
                pbfProto.Auth.UserIdAuthorizationResponse>(
                  this, METHODID_AUTHORIZE_USER_ID)))
          .addMethod(
            getLogoutUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Auth.UserLogoutRequest,
                pbfProto.Auth.UserLogoutResponse>(
                  this, METHODID_LOGOUT_USER)))
          .addMethod(
            getDeleteUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Auth.UserDeleteRequest,
                pbfProto.Auth.UserDeleteResponse>(
                  this, METHODID_DELETE_USER)))
          .addMethod(
            getGetUserDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Data.GetDataRequest,
                pbfProto.Data.GetDataResponse>(
                  this, METHODID_GET_USER_DATA)))
          .addMethod(
            getSubmitDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Data.SubmitDataRequest,
                pbfProto.Data.SubmitDataResponse>(
                  this, METHODID_SUBMIT_DATA)))
          .addMethod(
            getDeleteUserDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Data.DeleteDataRequest,
                pbfProto.Data.DeleteDataResponse>(
                  this, METHODID_DELETE_USER_DATA)))
          .addMethod(
            getGetDataAccessLogMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.Data.GetDataAccessLogRequest,
                pbfProto.Data.GetDataAccessLogResponse>(
                  this, METHODID_GET_DATA_ACCESS_LOG)))
          .addMethod(
            getSetConsentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.ConsentManager.SetConsentRequest,
                pbfProto.ConsentManager.SetConsentResponse>(
                  this, METHODID_SET_CONSENT)))
          .addMethod(
            getGetConsentHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.ConsentManager.GetConsentHistoryRequest,
                pbfProto.ConsentManager.GetConsentHistoryResponse>(
                  this, METHODID_GET_CONSENT_HISTORY)))
          .addMethod(
            getGetConsentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.ConsentManager.GetConsentRequest,
                pbfProto.ConsentManager.GetConsentResponse>(
                  this, METHODID_GET_CONSENT)))
          .addMethod(
            getGetConsentedDataTypesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pbfProto.ConsentManager.GetAuthorizedDataTypesRequest,
                pbfProto.ConsentManager.GetAuthorizedDataTypesResponse>(
                  this, METHODID_GET_CONSENTED_DATA_TYPES)))
          .build();
    }
  }

  /**
   */
  public static final class GatewaysStub extends io.grpc.stub.AbstractAsyncStub<GatewaysStub> {
    private GatewaysStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewaysStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewaysStub(channel, callOptions);
    }

    /**
     * <pre>
     * Registry Service
     * </pre>
     */
    public void registerUser(pbfProto.Auth.UserRegistrationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserRegistrationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void authenticateUser(pbfProto.Auth.UserAuthenticationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserAuthenticationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthenticateUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void authorizeUserRole(pbfProto.Auth.UserRoleAuthorizationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserRoleAuthorizationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthorizeUserRoleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void authorizeUserId(pbfProto.Auth.UserIdAuthorizationRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserIdAuthorizationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthorizeUserIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logoutUser(pbfProto.Auth.UserLogoutRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserLogoutResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLogoutUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(pbfProto.Auth.UserDeleteRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Auth.UserDeleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Data Service
     * </pre>
     */
    public void getUserData(pbfProto.Data.GetDataRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.GetDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitData(pbfProto.Data.SubmitDataRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.SubmitDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUserData(pbfProto.Data.DeleteDataRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.DeleteDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteUserDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getDataAccessLog(pbfProto.Data.GetDataAccessLogRequest request,
        io.grpc.stub.StreamObserver<pbfProto.Data.GetDataAccessLogResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDataAccessLogMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Consent Manager Service
     * </pre>
     */
    public void setConsent(pbfProto.ConsentManager.SetConsentRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.SetConsentResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetConsentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConsentHistory(pbfProto.ConsentManager.GetConsentHistoryRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetConsentHistoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConsentHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConsent(pbfProto.ConsentManager.GetConsentRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetConsentResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConsentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConsentedDataTypes(pbfProto.ConsentManager.GetAuthorizedDataTypesRequest request,
        io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetAuthorizedDataTypesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConsentedDataTypesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GatewaysBlockingStub extends io.grpc.stub.AbstractBlockingStub<GatewaysBlockingStub> {
    private GatewaysBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewaysBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewaysBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Registry Service
     * </pre>
     */
    public pbfProto.Auth.UserRegistrationResponse registerUser(pbfProto.Auth.UserRegistrationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Auth.UserAuthenticationResponse authenticateUser(pbfProto.Auth.UserAuthenticationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthenticateUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Auth.UserRoleAuthorizationResponse authorizeUserRole(pbfProto.Auth.UserRoleAuthorizationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthorizeUserRoleMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Auth.UserIdAuthorizationResponse authorizeUserId(pbfProto.Auth.UserIdAuthorizationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthorizeUserIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Auth.UserLogoutResponse logoutUser(pbfProto.Auth.UserLogoutRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLogoutUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Auth.UserDeleteResponse deleteUser(pbfProto.Auth.UserDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Data Service
     * </pre>
     */
    public pbfProto.Data.GetDataResponse getUserData(pbfProto.Data.GetDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Data.SubmitDataResponse submitData(pbfProto.Data.SubmitDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Data.DeleteDataResponse deleteUserData(pbfProto.Data.DeleteDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteUserDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.Data.GetDataAccessLogResponse getDataAccessLog(pbfProto.Data.GetDataAccessLogRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDataAccessLogMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Consent Manager Service
     * </pre>
     */
    public pbfProto.ConsentManager.SetConsentResponse setConsent(pbfProto.ConsentManager.SetConsentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetConsentMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.ConsentManager.GetConsentHistoryResponse getConsentHistory(pbfProto.ConsentManager.GetConsentHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConsentHistoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.ConsentManager.GetConsentResponse getConsent(pbfProto.ConsentManager.GetConsentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConsentMethod(), getCallOptions(), request);
    }

    /**
     */
    public pbfProto.ConsentManager.GetAuthorizedDataTypesResponse getConsentedDataTypes(pbfProto.ConsentManager.GetAuthorizedDataTypesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConsentedDataTypesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GatewaysFutureStub extends io.grpc.stub.AbstractFutureStub<GatewaysFutureStub> {
    private GatewaysFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GatewaysFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GatewaysFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Registry Service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Auth.UserRegistrationResponse> registerUser(
        pbfProto.Auth.UserRegistrationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Auth.UserAuthenticationResponse> authenticateUser(
        pbfProto.Auth.UserAuthenticationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthenticateUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Auth.UserRoleAuthorizationResponse> authorizeUserRole(
        pbfProto.Auth.UserRoleAuthorizationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthorizeUserRoleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Auth.UserIdAuthorizationResponse> authorizeUserId(
        pbfProto.Auth.UserIdAuthorizationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthorizeUserIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Auth.UserLogoutResponse> logoutUser(
        pbfProto.Auth.UserLogoutRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLogoutUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Auth.UserDeleteResponse> deleteUser(
        pbfProto.Auth.UserDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Data Service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Data.GetDataResponse> getUserData(
        pbfProto.Data.GetDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Data.SubmitDataResponse> submitData(
        pbfProto.Data.SubmitDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Data.DeleteDataResponse> deleteUserData(
        pbfProto.Data.DeleteDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteUserDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.Data.GetDataAccessLogResponse> getDataAccessLog(
        pbfProto.Data.GetDataAccessLogRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDataAccessLogMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Consent Manager Service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.ConsentManager.SetConsentResponse> setConsent(
        pbfProto.ConsentManager.SetConsentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetConsentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.ConsentManager.GetConsentHistoryResponse> getConsentHistory(
        pbfProto.ConsentManager.GetConsentHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConsentHistoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.ConsentManager.GetConsentResponse> getConsent(
        pbfProto.ConsentManager.GetConsentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConsentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pbfProto.ConsentManager.GetAuthorizedDataTypesResponse> getConsentedDataTypes(
        pbfProto.ConsentManager.GetAuthorizedDataTypesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConsentedDataTypesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_USER = 0;
  private static final int METHODID_AUTHENTICATE_USER = 1;
  private static final int METHODID_AUTHORIZE_USER_ROLE = 2;
  private static final int METHODID_AUTHORIZE_USER_ID = 3;
  private static final int METHODID_LOGOUT_USER = 4;
  private static final int METHODID_DELETE_USER = 5;
  private static final int METHODID_GET_USER_DATA = 6;
  private static final int METHODID_SUBMIT_DATA = 7;
  private static final int METHODID_DELETE_USER_DATA = 8;
  private static final int METHODID_GET_DATA_ACCESS_LOG = 9;
  private static final int METHODID_SET_CONSENT = 10;
  private static final int METHODID_GET_CONSENT_HISTORY = 11;
  private static final int METHODID_GET_CONSENT = 12;
  private static final int METHODID_GET_CONSENTED_DATA_TYPES = 13;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GatewaysImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GatewaysImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_USER:
          serviceImpl.registerUser((pbfProto.Auth.UserRegistrationRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Auth.UserRegistrationResponse>) responseObserver);
          break;
        case METHODID_AUTHENTICATE_USER:
          serviceImpl.authenticateUser((pbfProto.Auth.UserAuthenticationRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Auth.UserAuthenticationResponse>) responseObserver);
          break;
        case METHODID_AUTHORIZE_USER_ROLE:
          serviceImpl.authorizeUserRole((pbfProto.Auth.UserRoleAuthorizationRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Auth.UserRoleAuthorizationResponse>) responseObserver);
          break;
        case METHODID_AUTHORIZE_USER_ID:
          serviceImpl.authorizeUserId((pbfProto.Auth.UserIdAuthorizationRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Auth.UserIdAuthorizationResponse>) responseObserver);
          break;
        case METHODID_LOGOUT_USER:
          serviceImpl.logoutUser((pbfProto.Auth.UserLogoutRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Auth.UserLogoutResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((pbfProto.Auth.UserDeleteRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Auth.UserDeleteResponse>) responseObserver);
          break;
        case METHODID_GET_USER_DATA:
          serviceImpl.getUserData((pbfProto.Data.GetDataRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Data.GetDataResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_DATA:
          serviceImpl.submitData((pbfProto.Data.SubmitDataRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Data.SubmitDataResponse>) responseObserver);
          break;
        case METHODID_DELETE_USER_DATA:
          serviceImpl.deleteUserData((pbfProto.Data.DeleteDataRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Data.DeleteDataResponse>) responseObserver);
          break;
        case METHODID_GET_DATA_ACCESS_LOG:
          serviceImpl.getDataAccessLog((pbfProto.Data.GetDataAccessLogRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.Data.GetDataAccessLogResponse>) responseObserver);
          break;
        case METHODID_SET_CONSENT:
          serviceImpl.setConsent((pbfProto.ConsentManager.SetConsentRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.ConsentManager.SetConsentResponse>) responseObserver);
          break;
        case METHODID_GET_CONSENT_HISTORY:
          serviceImpl.getConsentHistory((pbfProto.ConsentManager.GetConsentHistoryRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetConsentHistoryResponse>) responseObserver);
          break;
        case METHODID_GET_CONSENT:
          serviceImpl.getConsent((pbfProto.ConsentManager.GetConsentRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetConsentResponse>) responseObserver);
          break;
        case METHODID_GET_CONSENTED_DATA_TYPES:
          serviceImpl.getConsentedDataTypes((pbfProto.ConsentManager.GetAuthorizedDataTypesRequest) request,
              (io.grpc.stub.StreamObserver<pbfProto.ConsentManager.GetAuthorizedDataTypesResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GatewaysBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GatewaysBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pbfProto.GatewaysOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Gateways");
    }
  }

  private static final class GatewaysFileDescriptorSupplier
      extends GatewaysBaseDescriptorSupplier {
    GatewaysFileDescriptorSupplier() {}
  }

  private static final class GatewaysMethodDescriptorSupplier
      extends GatewaysBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GatewaysMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GatewaysGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GatewaysFileDescriptorSupplier())
              .addMethod(getRegisterUserMethod())
              .addMethod(getAuthenticateUserMethod())
              .addMethod(getAuthorizeUserRoleMethod())
              .addMethod(getAuthorizeUserIdMethod())
              .addMethod(getLogoutUserMethod())
              .addMethod(getDeleteUserMethod())
              .addMethod(getGetUserDataMethod())
              .addMethod(getSubmitDataMethod())
              .addMethod(getDeleteUserDataMethod())
              .addMethod(getGetDataAccessLogMethod())
              .addMethod(getSetConsentMethod())
              .addMethod(getGetConsentHistoryMethod())
              .addMethod(getGetConsentMethod())
              .addMethod(getGetConsentedDataTypesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
