import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeViewerController {
    private IEmployeeViewer employeeViewer;
    private Scanner scanner;

    public EmployeeViewerController(IEmployeeViewer employeeViewer) {
        this.employeeViewer = employeeViewer;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n=== View Employees ===");
        System.out.println("1. View All Employees");
        System.out.println("2. Search by Role");
        System.out.println("3. Search by ID");
        System.out.println("4. Search by Name");
        System.out.println("5. Return to Main Menu");
        System.out.print("Select an option: ");
    }

    public void handleMenu() {
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    requestViewAllEmployees();
                    break;
                case "2":
                    requestSearchByRole();
                    break;
                case "3":
                    requestSearchById();
                    break;
                case "4":
                    requestSearchByName();
                    break;
                case "5":
                    running = false;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void requestViewAllEmployees() {
        System.out.println("\n--- All Employees ---");
        ArrayList<Employee> employees = employeeViewer.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in the system.");
            return;
        }

        System.out.println("Total Employees: " + employees.size());
        System.out.println("-------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
        System.out.println("-------------------------------------------------------");
    }

    public void requestSearchByRole() {
        System.out.println("\n--- Search by Role ---");
        System.out.println("Available roles:");
        System.out.println("1. Doctor");
        System.out.println("2. Nurse");
        System.out.println("3. Receptionist");
        System.out.println("4. Billing Officer");
        System.out.print("Select role: ");
        
        String choice = scanner.nextLine().trim();
        String role = "";
        
        switch (choice) {
            case "1":
                role = "Doctor";
                break;
            case "2":
                role = "Nurse";
                break;
            case "3":
                role = "Receptionist";
                break;
            case "4":
                role = "Billing Officer";
                break;
            default:
                System.out.println("Invalid role selection.");
                return;
        }

        ArrayList<Employee> employees = employeeViewer.getEmployeesByRole(role);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found with role: " + role);
            return;
        }

        System.out.println("\n--- Employees with role: " + role + " ---");
        System.out.println("Total: " + employees.size());
        System.out.println("-------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
        System.out.println("-------------------------------------------------------");
    }

    public void requestSearchById() {
        System.out.println("\n--- Search by ID ---");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("Error: Employee ID cannot be empty.");
            return;
        }

        Employee employee = employeeViewer.getEmployeeById(id);
        
        if (employee == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }

        System.out.println("\n--- Employee Details ---");
        System.out.println(employee.toString());
        System.out.println("-------------------------------------------------------");
    }

    public void requestSearchByName() {
        System.out.println("\n--- Search by Name ---");
        System.out.print("Enter name (partial match supported): ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }

        ArrayList<Employee> employees = employeeViewer.searchEmployeeByName(name);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found matching: " + name);
            return;
        }

        System.out.println("\n--- Search Results ---");
        System.out.println("Found " + employees.size() + " employee(s) matching '" + name + "':");
        System.out.println("-------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
        System.out.println("-------------------------------------------------------");
    }

    // Programmatic methods for testing without user input
    public ArrayList<Employee> requestViewAllEmployees(boolean silent) {
        return employeeViewer.getAllEmployees();
    }

    public ArrayList<Employee> requestSearchByRole(String role) {
        return employeeViewer.getEmployeesByRole(role);
    }

    public Employee requestSearchById(String id) {
        return employeeViewer.getEmployeeById(id);
    }

    public ArrayList<Employee> requestSearchByName(String name) {
        return employeeViewer.searchEmployeeByName(name);
    }
}
