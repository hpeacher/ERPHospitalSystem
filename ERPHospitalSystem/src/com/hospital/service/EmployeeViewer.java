package com.hospital.service;

import com.hospital.model.Employee;
import com.hospital.model.Doctor;
import com.hospital.model.Nurse;
import com.hospital.model.Hospital;
import java.util.ArrayList;
import java.util.List;

public class EmployeeViewer implements IEmployeeViewer {
    private Hospital hospital;
    private DoctorManager doctorManager;

    public EmployeeViewer(Hospital hospital, DoctorManager doctorManager) {
        this.hospital = hospital;
        this.doctorManager = doctorManager;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.addAll(doctorManager.getAllDoctors());
        allEmployees.addAll(hospital.getNurses());
        return allEmployees;
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        Doctor doctor = doctorManager.getDoctorById(employeeId);
        if (doctor != null) {
            return doctor;
        }

        Nurse nurse = hospital.getNurseById(employeeId);
        if (nurse != null) {
            return nurse;
        }

        return null;
    }

    @Override
    public List<Employee> getEmployeesByRole(String role) {
        List<Employee> filteredEmployees = new ArrayList<>();
        List<Employee> allEmployees = getAllEmployees();

        for (Employee emp : allEmployees) {
            if (emp.getRole().equalsIgnoreCase(role)) {
                filteredEmployees.add(emp);
            }
        }

        return filteredEmployees;
    }

    @Override
    public List<Employee> searchEmployeeByName(String name) {
        List<Employee> matchingEmployees = new ArrayList<>();
        List<Employee> allEmployees = getAllEmployees();

        for (Employee emp : allEmployees) {
            if (emp.getName().toLowerCase().contains(name.toLowerCase())) {
                matchingEmployees.add(emp);
            }
        }

        return matchingEmployees;
    }
}
