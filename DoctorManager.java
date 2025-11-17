import java.util.ArrayList;

public class DoctorManager implements IDoctorManager {
    private ArrayList<Doctor> doctors;

    public DoctorManager() {
        this.doctors = new ArrayList<Doctor>();
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        // Check if doctor ID already exists
        if (getDoctorById(doctor.getDoctorId()) != null) {
            System.out.println("Error: Doctor ID " + doctor.getDoctorId() + " already exists.");
            return false;
        }

        doctors.add(doctor);
        System.out.println("Doctor successfully added: " + doctor.getName());
        return true;
    }

    @Override
    public boolean removeDoctor(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        
        if (doctor == null) {
            System.out.println("Error: Doctor with ID " + doctorId + " not found.");
            return false;
        }

        // Check if doctor has active patients
        if (doctor.hasActivePatients()) {
            System.out.println("Error: Cannot remove doctor with active patients. " +
                             "Doctor " + doctor.getName() + " has " + 
                             doctor.getAssignedPatients().size() + " assigned patient(s).");
            return false;
        }

        doctors.remove(doctor);
        System.out.println("Doctor successfully removed: " + doctor.getName());
        return true;
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Doctor> getAllDoctors() {
        return new ArrayList<Doctor>(doctors);
    }

    public int getDoctorCount() {
        return doctors.size();
    }
}
