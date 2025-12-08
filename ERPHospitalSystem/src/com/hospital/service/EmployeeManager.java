package com.hospital.service;

import com.hospital.model.Employee;
import com.hospital.repository.IEmployeeRepository;
import com.hospital.service.IEmployeeManager;

import java.util.List;

public class EmployeeManager implements IEmployeeManager {

    private final IEmployeeRepository repo;

    public EmployeeManager(IEmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        if (!validate(employee)) return false;
        if (employeeExists(employee.getEmployeeId())) return false;
        return repo.saveEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee updatedEmployee) {
        if (!validate(updatedEmployee)) return false;
        return repo.updateEmployee(updatedEmployee);
    }

    @Override
    public boolean removeEmployee(String employeeId) {
        return repo.deleteEmployee(employeeId);
    }

    @Override
    public boolean employeeExists(String id) {
        return repo.getEmployeeById(id) != null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repo.getAllEmployees();
    }

    private boolean validate(Employee e) {
        return e.getEmployeeId() != null && !e.getEmployeeId().isEmpty()
            && e.getName() != null && !e.getName().isEmpty()
            && e.getRole() != null && !e.getRole().isEmpty()
            && e.getDepartment() != null && !e.getDepartment().isEmpty();
    }
}
