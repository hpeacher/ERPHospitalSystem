public class PatientMedicalInfo {
    private String patientId;
    private String gender;
    private String bloodType;
    private String height;
    private String weight;

    // Constructor
    public PatientMedicalInfo(String patientId, String gender, String bloodType, String height, String weight) {
        this.patientId = patientId;
        this.gender = gender;
        this.bloodType = bloodType;
        this.height = height;
        this.weight = weight;
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    // Setters
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    public PatientMedicalInfo() {
    }
}