package com.hospital.command.impl;

import com.hospital.command.ICommand;

public class DiagnosisCommand implements ICommand {
    @Override
    public String getToken() {
        return "diagnose";
    }

    @Override
    public void execute() {
        System.out.println("TEMP");
    }
}
