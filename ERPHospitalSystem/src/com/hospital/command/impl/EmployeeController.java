package com.hospital.command.impl;

import com.hospital.model.Employee;
import com.hospital.service.IEmployeeManager;

public class EmployeeController {

    private final IEmployeeManager manager;

    public EmployeeController(IEmployeeManager manager) {
        this.manager = manager;
    }

    public String add(Employee e) {
        return manager.addEmployee(e) ? "success" : "failure";
    }

    public String update(Employee e) {
        return manager.updateEmployee(e) ? "success" : "failure";
    }

    public String remove(String id) {
        return manager.removeEmployee(id) ? "success" : "failure";
    }
}
