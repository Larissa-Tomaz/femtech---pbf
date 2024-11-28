import time
import grpc
import sys
from locust import User, task, between
import grpc.experimental.gevent as grpc_gevent
from grpc_interceptor import ClientInterceptor

# Patch grpc to use gevent
grpc_gevent.init_gevent()

# Add the path to the generated gRPC files
sys.path.append('/home/larissa-tomaz/pbf/App/protos/src/generated/main/grpc/')

# Import the generated gRPC files
import gateways_pb2_grpc
import auth_pb2
import data_pb2
import consentManager_pb2


class LocustInterceptor(ClientInterceptor):
    def __init__(self, environment, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.env = environment

    def intercept(self, method, request_or_iterator, call_details):
        response = None
        exception = None
        start_perf_counter = time.perf_counter()
        response_length = 0
        try:
            response = method(request_or_iterator, call_details)
            if response and hasattr(response, 'result'):
                response_length = response.result().ByteSize()
        except grpc.RpcError as e:
            exception = e

        print(f"Request to {call_details.method} took {(time.perf_counter() - start_perf_counter) * 1000} ms")
        self.env.events.request.fire(
            request_type="grpc",
            name=call_details.method,
            response_time=(time.perf_counter() - start_perf_counter) * 1000,
            response_length=response_length,
            response=response,
            context=None,
            exception=exception,
        )
        return response


class GrpcClient:
    def __init__(self, host='localhost:8081', environment=None):
        self.channel = grpc.insecure_channel(host)
        print(f"Connecting to gRPC server at {host}")
        if environment:
            interceptor = LocustInterceptor(environment=environment)
            self.channel = grpc.intercept_channel(self.channel, interceptor)
        self.stub = gateways_pb2_grpc.GatewaysStub(self.channel)  # Stub for the Gateways service

    def register_user(self, username, password):
        request = auth_pb2.UserRegistrationRequest(
            username=username,
            password=password,
            role=auth_pb2.FEMTECH_USER,
            accountData=auth_pb2.UserAccountData(profile_name="Test Profile", registration_date="20231006_120000", payment_plan="basic", subscription_status=True),
            profileData=auth_pb2.UserProfileData(age="25", menstrualCycleLength="28", periodLength="5", mainContraceptiveMethod="None")
        )
        return self.stub.RegisterUser(request)

    def authenticate_user(self, username, password):
        request = auth_pb2.UserAuthenticationRequest(username=username, password=password)
        return self.stub.AuthenticateUser(request)

    def set_consent(self, token):
        # Create a consent matrix with the given settings
        consent_entries = [
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.SERVICE_PROVISION, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.SERVICE_PROVISION, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.SERVICE_PROVISION, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.MARKETING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.MARKETING, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.MARKETING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.ANALYTICS, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.ANALYTICS, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.ANALYTICS, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.RESEARCH, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.RESEARCH, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.RESEARCH, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.SHARING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.SHARING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.SHARING, consentGiven=False),
        ]
        request = consentManager_pb2.SetConsentRequest(privacySettings=consent_entries, token=token)
        return self.stub.SetConsent(request)

    def submit_data(self, token):
        fertility_log = data_pb2.FertilityDailyLog(date="2023-10-01", temperature=[36.5, 36.7, 36.8], bpm=[75, 80, 78], bleeding="Clots", sexualActivity="Protected")
        request = data_pb2.SubmitDataRequest(fertilityDailyLog=fertility_log, token=token)
        return self.stub.SubmitData(request)


import time
import grpc
import sys
from locust import User, task, between
import grpc.experimental.gevent as grpc_gevent
from grpc_interceptor import ClientInterceptor

# Patch grpc to use gevent
grpc_gevent.init_gevent()

# Add the path to the generated gRPC files
sys.path.append('/home/larissa-tomaz/pbf/App/protos/src/generated/main/grpc/')

# Import the generated gRPC files
import gateways_pb2_grpc
import auth_pb2
import data_pb2
import consentManager_pb2


class LocustInterceptor(ClientInterceptor):
    def __init__(self, environment, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.env = environment

    def intercept(self, method, request_or_iterator, call_details):
        response = None
        exception = None
        start_perf_counter = time.perf_counter()
        response_length = 0
        try:
            response = method(request_or_iterator, call_details)
            if response and hasattr(response, 'result'):
                response_length = response.result().ByteSize()
        except grpc.RpcError as e:
            exception = e

        print(f"Request to {call_details.method} took {(time.perf_counter() - start_perf_counter) * 1000} ms")
        self.env.events.request.fire(
            request_type="grpc",
            name=call_details.method,
            response_time=(time.perf_counter() - start_perf_counter) * 1000,
            response_length=response_length,
            response=response,
            context=None,
            exception=exception,
        )
        return response


class GrpcClient:
    def __init__(self, host='localhost:8081', environment=None):
        self.channel = grpc.insecure_channel(host)
        print(f"Connecting to gRPC server at {host}")
        if environment:
            interceptor = LocustInterceptor(environment=environment)
            self.channel = grpc.intercept_channel(self.channel, interceptor)
        self.stub = gateways_pb2_grpc.GatewaysStub(self.channel)  # Stub for the Gateways service

    def register_user(self, username, password):
        request = auth_pb2.UserRegistrationRequest(
            username=username,
            password=password,
            role=auth_pb2.FEMTECH_USER,
            accountData=auth_pb2.UserAccountData(profile_name="Test Profile", registration_date="20231006_120000", payment_plan="basic", subscription_status=True),
            profileData=auth_pb2.UserProfileData(age="25", menstrualCycleLength="28", periodLength="5", mainContraceptiveMethod="None")
        )
        return self.stub.RegisterUser(request)

    def authenticate_user(self, username, password):
        request = auth_pb2.UserAuthenticationRequest(username=username, password=password)
        return self.stub.AuthenticateUser(request)

    def set_consent(self, token):
        # Create a consent matrix with the given settings
        consent_entries = [
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.SERVICE_PROVISION, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.SERVICE_PROVISION, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.SERVICE_PROVISION, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.MARKETING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.MARKETING, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.MARKETING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.ANALYTICS, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.ANALYTICS, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.ANALYTICS, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.RESEARCH, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.RESEARCH, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.RESEARCH, consentGiven=False),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.ACCOUNT_DATA, purpose=consentManager_pb2.SHARING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.PROFILE_DATA, purpose=consentManager_pb2.SHARING, consentGiven=True),
            consentManager_pb2.ConsentEntry(data_type=consentManager_pb2.FERTILITY_DATA, purpose=consentManager_pb2.SHARING, consentGiven=False),
        ]
        request = consentManager_pb2.SetConsentRequest(privacySettings=consent_entries, token=token)
        return self.stub.SetConsent(request)

    def submit_data(self, token):
        fertility_log = data_pb2.FertilityDailyLog(
            date="2023-10-01",
            temperature=[36.5, 36.7, 36.8],
            bpm=[75, 80, 78],
            bleeding="Clots",
            sexualActivity="Protected"
        )
        request = data_pb2.SubmitDataRequest(fertilityDailyLog=fertility_log, token=token)
        return self.stub.SubmitData(request)


class GrpcUser(User):
    wait_time = between(1, 2)
    username_counter = 1
    fixed_password = "password123"

    def on_start(self):
        # Initialize the gRPC client
        self.client = GrpcClient(environment=self.environment)
        self.token = None

    @task
    def authenticate(self):
        # Only authenticate user
        username = f"user{GrpcUser.username_counter}"
        GrpcUser.username_counter += 100

        auth_response = self.client.authenticate_user(username, self.fixed_password)
        print(f"User {username} authenticated with response: {auth_response}")
        if auth_response.token:
            self.token = auth_response.token

    @task
    def authenticate_and_submit_consent(self):
        # Authenticate and submit consent
        username = f"user{GrpcUser.username_counter}"
        GrpcUser.username_counter += 250

        auth_response = self.client.authenticate_user(username, self.fixed_password)
        print(f"User {username} authenticated with response: {auth_response}")

        if auth_response.token:
            # Submit consent
            consent_response = self.client.set_consent(auth_response.token)
            print(f"Consent set for user {username} with response: {consent_response}")

    @task
    def authenticate_and_submit_data(self):
        # Authenticate and submit data
        username = f"user{GrpcUser.username_counter}"
        GrpcUser.username_counter += 1

        auth_response = self.client.authenticate_user(username, self.fixed_password)
        print(f"User {username} authenticated with response: {auth_response}")

        if auth_response.token:
            # Submit data
            data_response = self.client.submit_data(auth_response.token)
            print(f"Data submitted for user {username} with response: {data_response}")


    
