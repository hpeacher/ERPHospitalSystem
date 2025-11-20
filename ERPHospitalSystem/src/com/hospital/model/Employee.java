package com.hospital.model;

public class Employee {
    protected String employeeId;
    protected String name;
    protected String role;
    protected String department;
    protected String contactInfo;

    public Employee(String employeeId, String name, String role, String department, String contactInfo) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.department = department;
        this.contactInfo = contactInfo;
    }

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "ID: " + employeeId + " | Name: " + name + " | Role: " + role + 
               " | Dept: " + department + " | Contact: " + contactInfo;
    }
}
