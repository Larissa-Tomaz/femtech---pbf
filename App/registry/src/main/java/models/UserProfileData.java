package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

public class UserProfileData {
    @JsonProperty("age")  // Matches "age" in JSON
    private String age;

    @JsonProperty("menstrualCycleLength")  // Matches "menstrualCycleLength" in JSON
    private String menstrualCycleLength;

    @JsonProperty("periodLength")  // Matches "periodLength" in JSON
    private String periodLength;

    @JsonProperty("mainContraceptiveMethod")  // Matches "mainContraceptiveMethod" in JSON
    private String mainContraceptiveMethod;

    @JsonCreator
    public UserProfileData(@JsonProperty("age") String age, @JsonProperty("menstrualCycleLength") String menstrualCycleLength, @JsonProperty("periodLength") String periodLength,  @JsonProperty("mainContraceptiveMethod") String mainContraceptiveMethod) {
        this.age = age;
        this.menstrualCycleLength = menstrualCycleLength;
        this.periodLength = periodLength;
        this.mainContraceptiveMethod = mainContraceptiveMethod;
    }

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

    @Override
    public String toString() {
        return "UserProfileData{" +
                "age='" + age + '\'' +
                ", menstrualCycleLength='" + menstrualCycleLength + '\'' +
                ", periodLength='" + periodLength + '\'' +
                ", mainContraceptiveMethod='" + mainContraceptiveMethod + '\'' +
                '}';
    }
}
