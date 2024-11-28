import com.owlike.genson.annotation.JsonProperty;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.List;
import java.util.Objects;

@DataType()
public final class DataAccessLog {

    @Property
    private final String logId;

    @Property
    private final String date;

    @Property
    private final String purpose;

    @Property
    private final List<String> dataTypes;

    @Property
    private final String entity;

    @Property
    private final String role;

    @Property
    private final String status;

    // Constructor for DataAccessLog
    public DataAccessLog(
            @JsonProperty("logId") final String logId,
            @JsonProperty("date") final String date,
            @JsonProperty("purpose") final String purpose,
            @JsonProperty("dataTypes") final List<String> dataTypes,
            @JsonProperty("entity") final String entity,
            @JsonProperty("role") final String role,
            @JsonProperty("status") final String status) {
        this.logId = logId;
        this.date = date;
        this.purpose = purpose;
        this.dataTypes = dataTypes;
        this.entity = entity;
        this.role = role;
        this.status = status;
    }

    // Getters for all properties
    public String getUserId() {
        return logId;
    }

    public String getDate() {
        return date;
    }

    public String getPurpose() {
        return purpose;
    }

    public List<String> getDataTypes() {
        return dataTypes;
    }

    public String getEntity() {
        return entity;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    // Overriding the hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(logId, date, purpose, dataTypes, entity, role, status);
    }

    // Overriding the equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DataAccessLog other = (DataAccessLog) obj;
        return Objects.equals(logId, other.logId) &&
                Objects.equals(date, other.date) &&
                Objects.equals(purpose, other.purpose) &&
                Objects.equals(dataTypes, other.dataTypes) &&
                Objects.equals(entity, other.entity) &&
                Objects.equals(role, other.role) &&
                Objects.equals(status, other.status);
    }

    // Overriding the toString method for easy printing
    @Override
    public String toString() {
        return "DataAccessLog{" +
                "logId='" + logId + '\'' +
                ", date='" + date + '\'' +
                ", purpose='" + purpose + '\'' +
                ", dataTypes=" + dataTypes +
                ", entity='" + entity + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}