package com.hospital.command.impl;

import com.hospital.command.ICommand;
import com.hospital.model.Employee;
import com.hospital.command.impl.EmployeeController;

public class ManageEmployeesCommand implements ICommand {

    private final EmployeeController controller;
    private final String action;
    private final Employee employeeData;
    private final String employeeId; // for delete

    /**
     * Constructor for add or update operations.
     */
    public ManageEmployeesCommand(EmployeeController controller, String action, Employee employeeData) {
        this.controller = controller;
        this.action = action;
        this.employeeData = employeeData;
        this.employeeId = null;
    }

    /**
     * Constructor for remove operation.
     */
    public ManageEmployeesCommand(EmployeeController controller, String action, String employeeId) {
        this.controller = controller;
        this.action = action;
        this.employeeId = employeeId;
        this.employeeData = null;
    }

    @Override
    public String getToken() {
        return "manage_employees";
    }

    @Override
    public void execute() {

        switch (action.toLowerCase()) {
            case "add":
                System.out.println(controller.add(employeeData));
                break;

            case "update":
                System.out.println(controller.update(employeeData));
                break;

            case "remove":
                System.out.println(controller.remove(employeeId));
                break;

            default:
                System.out.println("Invalid employee management command.");
        }
    }
}

