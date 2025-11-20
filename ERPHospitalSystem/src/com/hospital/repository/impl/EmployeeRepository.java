package com.hospital.repository.impl;

import com.hospital.model.Employee;
import com.hospital.repository.IEmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employees.stream()
                .filter(e -> e.getEmployeeId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean saveEmployee(Employee employee) {
        return employees.add(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        int idx = -1;

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId().equals(employee.getEmployeeId())) {
                idx = i;
                break;
            }
        }

        if (idx == -1) return false;

        employees.set(idx, employee);
        return true;
    }

    @Override
    public boolean deleteEmployee(String id) {
        return employees.removeIf(e -> e.getEmployeeId().equals(id));
    }
}
