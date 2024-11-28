import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class UserProfileData {

    @Property()
    private String age;

    @Property()
    private String menstrualCycleLength;

    @Property()
    private String periodLength;

    @Property()
    private String mainContraceptiveMethod;

    public UserProfileData(@JsonProperty("age") String age,
                           @JsonProperty("menstrualCycleLength") String menstrualCycleLength,
                           @JsonProperty("periodLength") String periodLength,
                           @JsonProperty("mainContraceptiveMethod") String mainContraceptiveMethod) {
        this.age = age;
        this.menstrualCycleLength = menstrualCycleLength;
        this.periodLength = periodLength;
        this.mainContraceptiveMethod = mainContraceptiveMethod;
    }

    // Getters and Setters
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMenstrualCycleLength() {
        return menstrualCycleLength;
    }

    public void setMenstrualCycleLength(String menstrualCycleLength) {
        this.menstrualCycleLength = menstrualCycleLength;
    }

    public String getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(String periodLength) {
        this.periodLength = periodLength;
    }

    public String getMainContraceptiveMethod() {
        return mainContraceptiveMethod;
    }

    public void setMainContraceptiveMethod(String mainContraceptiveMethod) {
        this.mainContraceptiveMethod = mainContraceptiveMethod;
    }
}
