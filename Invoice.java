public class Invoice {
    private int id;
    private double cost;
    private String patientId;
    private String insurance;
    private boolean finalized;

    public Invoice(int id, double cost, String patientId, String insurance) {
        this.id = id;
        this.cost = cost;
        this.patientId = patientId;
        this.insurance = insurance;
        this.finalized = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }
}
