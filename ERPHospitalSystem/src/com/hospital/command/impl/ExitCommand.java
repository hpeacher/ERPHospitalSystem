package com.hospital.command.impl;

import com.hospital.command.ICommand;

public class ExitCommand implements ICommand {
    private final Runnable shutdownCallback;

    public ExitCommand(Runnable shutdownCallback) {
        this.shutdownCallback = shutdownCallback;
    }

    @Override
    public String getToken() {
        return "exit";
    }

    @Override
    public void execute() {
        System.out.println("Exiting system. Goodbye!");
        shutdownCallback.run(); // signal shutdown
    }
}
