package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileData {
    @JsonProperty("age")
    private String age;

    @JsonProperty("menstrualCycleLength")
    private String menstrualCycleLength;

    @JsonProperty("periodLength")
    private String periodLength;

    @JsonProperty("mainContraceptiveMethod")
    private String mainContraceptiveMethod;

    // Constructor
    public ProfileData(@JsonProperty("age") String age,
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

    @Override
    public String toString() {
        return "ProfileData{" +
                "age='" + age + '\'' +
                ", menstrualCycleLength='" + menstrualCycleLength + '\'' +
                ", periodLength='" + periodLength + '\'' +
                ", mainContraceptiveMethod='" + mainContraceptiveMethod + '\'' +
                '}';
    }
}
