import java.util.ArrayList;

interface IHospital {
    boolean patientDischarged(String patientId);

    boolean patientAdmitted(String patientId);

    boolean queryPatient(String patientId);

    Nurse getNurseById(String nurseId);

    Nurse findNurseByPatientId(String patientId);

    int getCapacity();

    ArrayList<String> getPatients();

    ArrayList<Nurse> getNurses();
}

public class Hospital implements IHospital {
    private int capacity;
    private ArrayList<String> patients;
    private ArrayList<Nurse> nurses;

    public Hospital(int capacity) {
        this.capacity = capacity;
        this.patients = new ArrayList<String>();
        this.nurses = new ArrayList<Nurse>();
    }

    public boolean patientDischarged(String patientId) {
        if (patients.contains(patientId)) {
            patients.remove(patientId);
            System.out.println("Patient " + patientId + " has been discharged.");
            return true;
        } else {
            System.out.println("Patient " + patientId + " is not found in the hospital.");
            return false;
        }
    }

    public boolean patientAdmitted(String patientId) {
        if (patients.size() < capacity) {
            patients.add(patientId);
            System.out.println("Patient " + patientId + " has been admitted.");
            boolean assigned = assignToNurse(patientId);
            return assigned;
        } else {
            System.out.println("Hospital is at full capacity!");
            return false;
        }
    }

    public boolean queryPatient(String patientId) {
        if (patients.contains(patientId)) {
            System.out.println("Patient " + patientId + " is admitted in the hospital.");
            return true;
        } else {
            System.out.println("Patient " + patientId + " is not found in the hospital.");
            return false;
        }
    }

    public boolean assignToNurse(String patientId) {
        if (nurses.isEmpty()) {
            System.out.println("No nurses available to assign Patient " + patientId + ".");
            return false;
        }
        // Simple round-robin assignment
        Nurse nurse = nurses.get(patients.size() % nurses.size());
        nurse.assignPatient(patientId);
        return true;
    }

    public Nurse getNurseById(String nurseId) {
        for (Nurse nurse : nurses) {
            if (nurse.getNurseId().equals(nurseId)) {
                return nurse;
            }
        }
        return null;
    }

    public Nurse findNurseByPatientId(String patientId) {
        for (Nurse nurse : nurses) {
            if (nurse.getAssignedPatients().contains(patientId)) {
                return nurse;
            }
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getPatients() {
        return patients;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void addNurse(Nurse nurse) {
        this.nurses.add(nurse);
    }

    public void removeNurse(Nurse nurse) {
        this.nurses.remove(nurse);
    }
}
