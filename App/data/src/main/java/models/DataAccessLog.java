package models;

import java.util.List;
import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import pbfProto.Auth;

public class DataAccessLog {
    
    @JsonProperty("logId")
    private String logId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("dataTypes")
    private List<String> dataTypes;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("role")
    private Auth.ROLE role;

    @JsonProperty("status")
    private String status;

    // Constructor
    @JsonCreator
    public DataAccessLog(
            @JsonProperty("logId") String logId, 
            @JsonProperty("date") String date, 
            @JsonProperty("purpose") String purpose, 
            @JsonProperty("dataTypes") List<String> dataTypes, 
            @JsonProperty("entity") String entity, 
            @JsonProperty("role") Auth.ROLE role, 
            @JsonProperty("status") String status) {
        this.logId = logId;
        this.date = date;
        this.purpose = purpose;
        this.dataTypes = dataTypes;
        this.entity = entity;
        this.role = role;
        this.status = status;
    }

    // Getters and Setters

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<String> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<String> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Auth.ROLE getRole() {
        return role;
    }

    public void setRole(Auth.ROLE role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString() method for easy printing
    @Override
    public String toString() {
        return "DataAccessLog{" +
                "logId='" + logId + '\'' +
                ", date='" + date + '\'' +
                ", purpose='" + purpose + '\'' +
                ", dataTypes=" + dataTypes +
                ", entity='" + entity + '\'' +
                ", role=" + role +
                ", status='" + status + '\'' +
                '}';
    }
}
