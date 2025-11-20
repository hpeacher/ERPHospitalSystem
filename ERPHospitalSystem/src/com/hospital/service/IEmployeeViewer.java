package com.hospital.service;

import com.hospital.model.Employee;
import java.util.List;

public interface IEmployeeViewer {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String employeeId);
    List<Employee> getEmployeesByRole(String role);
    List<Employee> searchEmployeeByName(String name);
}
