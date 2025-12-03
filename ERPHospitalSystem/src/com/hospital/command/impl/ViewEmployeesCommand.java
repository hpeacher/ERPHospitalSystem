package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.service.IEmployeeViewer;
import com.hospital.model.Employee;
import java.util.List;
import java.util.Scanner;

public class ViewEmployeesCommand implements ICommand {
    private IEmployeeViewer employeeViewer;
    private Scanner scanner;

    public ViewEmployeesCommand(IEmployeeViewer employeeViewer, Scanner scanner) {
        this.employeeViewer = employeeViewer;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("\n=== View Employees ===");
            System.out.println("1. View All Employees");
            System.out.println("2. Search by Role");
            System.out.println("3. Search by ID");
            System.out.println("4. Search by Name");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllEmployees();
                    break;
                case "2":
                    searchByRole();
                    break;
                case "3":
                    searchById();
                    break;
                case "4":
                    searchByName();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllEmployees() {
        List<Employee> employees = employeeViewer.getAllEmployees();
        displayEmployees(employees, "All Employees");
    }

    private void searchByRole() {
        System.out.print("Enter role (Doctor/Nurse): ");
        String role = scanner.nextLine().trim();
        List<Employee> employees = employeeViewer.getEmployeesByRole(role);
        displayEmployees(employees, "Employees with role: " + role);
    }

    private void searchById() {
        System.out.print("Enter Employee ID: ");
        String employeeId = scanner.nextLine().trim();
        Employee employee = employeeViewer.getEmployeeById(employeeId);
        
        if (employee != null) {
            System.out.println("\n=== Employee Found ===");
            System.out.println(employee);
        } else {
            System.out.println("No employee found with ID: " + employeeId);
        }
    }

    private void searchByName() {
        System.out.print("Enter name (or part of name): ");
        String name = scanner.nextLine().trim();
        List<Employee> employees = employeeViewer.searchEmployeeByName(name);
        displayEmployees(employees, "Employees matching: " + name);
    }

    private void displayEmployees(List<Employee> employees, String title) {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n=== " + title + " ===");
        System.out.println("Total: " + employees.size());
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Override
    public String getToken() {
        return "viewemployees";
    }
}
