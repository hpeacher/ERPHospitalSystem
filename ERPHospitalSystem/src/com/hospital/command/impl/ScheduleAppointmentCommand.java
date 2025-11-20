package com.hospital.command.impl;

import java.util.Scanner;
import com.hospital.command.ICommand;
import com.hospital.model.Appointment;
import com.hospital.service.AppointmentScheduler;

public class ScheduleAppointmentCommand implements ICommand {

    private final AppointmentScheduler scheduler;
    private final Scanner sc;

    public ScheduleAppointmentCommand(AppointmentScheduler scheduler, Scanner sc) {
        this.scheduler = scheduler;
        this.sc = sc;
    }

    @Override
    public String getToken() {
        return "schedule_appointment";
    }

    @Override
    public void execute() {

        try {
            System.out.print("Enter patient ID: ");
            String patientId = sc.nextLine();

            System.out.print("Enter doctor name: ");
            String doctorName = sc.nextLine();

            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Enter appointment time (HH:MM): ");
            String time = sc.nextLine();

            Appointment appt = scheduler.scheduleAppointment(patientId, doctorName, date, time);

            System.out.println("Appointment scheduled! ID = " + appt.getAppointmentId());

        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
        }
    }
}
