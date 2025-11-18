package com.hospital.command.impl;

import com.hospital.command.ICommand;

public class ScheduleAppointmentCommand implements ICommand {
    @Override
    public String getToken() {
        return "schedule_appointment";
    }

    @Override
    public void execute() {
        System.out.println("TEMP for implementation after schedule appointment merge.");
    }
}
