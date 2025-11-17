import java.util.ArrayList;

public class EmployeeViewer implements IEmployeeViewer {
    private Hospital hospital;
    private DoctorManager doctorManager;

    public EmployeeViewer(Hospital hospital, DoctorManager doctorManager) {
        this.hospital = hospital;
        this.doctorManager = doctorManager;
    }

    @Override
    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> allEmployees = new ArrayList<Employee>();
        
        // Add all doctors
        for (Doctor doctor : doctorManager.getAllDoctors()) {
            allEmployees.add(doctor);
        }
        
        // Add all nurses
        for (Nurse nurse : hospital.getNurses()) {
            Employee nurseEmployee = new Employee(
                nurse.getNurseId(),
                nurse.getName(),
                "Nurse",
                "Nursing",
                "N/A"
            );
            allEmployees.add(nurseEmployee);
        }
        
        return allEmployees;
    }

    @Override
    public Employee getEmployeeById(String id) {
        // Search in doctors
        Doctor doctor = doctorManager.getDoctorById(id);
        if (doctor != null) {
            return doctor;
        }
        
        // Search in nurses
        Nurse nurse = hospital.getNurseById(id);
        if (nurse != null) {
            return new Employee(
                nurse.getNurseId(),
                nurse.getName(),
                "Nurse",
                "Nursing",
                "N/A"
            );
        }
        
        return null;
    }

    @Override
    public ArrayList<Employee> getEmployeesByRole(String role) {
        ArrayList<Employee> filteredEmployees = new ArrayList<Employee>();
        
        if (role.equalsIgnoreCase("Doctor")) {
            for (Doctor doctor : doctorManager.getAllDoctors()) {
                filteredEmployees.add(doctor);
            }
        } else if (role.equalsIgnoreCase("Nurse")) {
            for (Nurse nurse : hospital.getNurses()) {
                Employee nurseEmployee = new Employee(
                    nurse.getNurseId(),
                    nurse.getName(),
                    "Nurse",
                    "Nursing",
                    "N/A"
                );
                filteredEmployees.add(nurseEmployee);
            }
        } else if (role.equalsIgnoreCase("Receptionist")) {
            // Placeholder for future receptionist implementation
            System.out.println("Receptionist role not yet implemented.");
        } else if (role.equalsIgnoreCase("Billing Officer")) {
            // Placeholder for future billing officer implementation
            System.out.println("Billing Officer role not yet implemented.");
        }
        
        return filteredEmployees;
    }

    @Override
    public ArrayList<Employee> searchEmployeeByName(String name) {
        ArrayList<Employee> matchingEmployees = new ArrayList<Employee>();
        String searchTerm = name.toLowerCase();
        
        // Search in all employees
        for (Employee employee : getAllEmployees()) {
            if (employee.getName().toLowerCase().contains(searchTerm)) {
                matchingEmployees.add(employee);
            }
        }
        
        return matchingEmployees;
    }
}
