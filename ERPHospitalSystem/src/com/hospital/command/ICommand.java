package com.hospital.command;

public interface ICommand {
    String getToken();

    void execute();
}
