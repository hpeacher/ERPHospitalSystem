package com.hospital.repository;

import com.hospital.model.Employee;
import java.util.List;

public interface IEmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id);

    boolean saveEmployee(Employee employee);

    boolean updateEmployee(Employee employee);

    boolean deleteEmployee(String id);
}

