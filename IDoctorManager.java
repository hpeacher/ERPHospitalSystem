import java.util.ArrayList;

public interface IDoctorManager {
    boolean addDoctor(Doctor doctor);
    boolean removeDoctor(String doctorId);
    Doctor getDoctorById(String doctorId);
    ArrayList<Doctor> getAllDoctors();
}
