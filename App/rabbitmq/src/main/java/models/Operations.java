package models;

public enum Operations {

    // Registry Service
    NEW_USER_REQUEST,
    AUTHENTICATION_REQUEST,
    AUTHORIZATION_REQUEST,
    ROLE_AUTHORIZATION_REQUEST,
    ID_AUTHORIZATION_REQUEST,
    LOGOUT_REQUEST,
    DELETE_USER_REQUEST,
    GET_ACCOUNT_DATA,
    GET_PROFILE_DATA,

    // Data Service
    SUBMIT_USER_DATA,
    GET_USER_DATA,
    GET_DATA_ACCESS_LOGS,
    DELETE_USER_DATA,

    //Consent Service
    SET_CONSENT,
    GET_CONSENT_HISTORY,
    GET_CONSENT,
    DATA_TYPES_AUTHORIZATION_REQUEST
    
}