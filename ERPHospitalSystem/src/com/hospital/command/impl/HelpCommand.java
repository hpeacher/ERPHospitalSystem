package com.hospital.command.impl;

import com.hospital.command.ICommand;

public class HelpCommand implements ICommand {
    private final DisplayContainer container;

    public HelpCommand(DisplayContainer displayContainer) {
        this.container = displayContainer;
    }

    @Override
    public String getToken() {
        return "help";
    }

    @Override
    public void execute() {
        container.showAvailableCommands();
    }
}
