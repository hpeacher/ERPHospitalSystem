import java.util.ArrayList;

public interface IEmployeeViewer {
    ArrayList<Employee> getAllEmployees();
    Employee getEmployeeById(String id);
    ArrayList<Employee> getEmployeesByRole(String role);
    ArrayList<Employee> searchEmployeeByName(String name);
}
