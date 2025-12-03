package com.hospital.service;

import com.hospital.model.Employee;
import java.util.List;

public interface IEmployeeManager {

    boolean addEmployee(Employee employee);

    boolean updateEmployee(Employee updatedEmployee);

    boolean removeEmployee(String employeeId);

    boolean employeeExists(String employeeId);

    List<Employee> getAllEmployees();
}
