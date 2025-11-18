package com.hospital.command.impl;

import java.util.*;

import com.hospital.command.ICommand;

public class DisplayContainer {
    private final Map<String, ICommand> commandMap = new HashMap<>();

    public void registerCommand(ICommand command) {
        commandMap.put(command.getToken().toLowerCase(), command);
    }

    public void acceptUserInput(String input) {
        ICommand command = commandMap.get(input.toLowerCase());
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Unknown command: " + input);
        }
    }

    public void showAvailableCommands() {
        System.out.println("Available commands:");
        for (String token : commandMap.keySet()) {
            System.out.println("- " + token);
        }
    }

    public void showDefaultScreen() {
        System.out.println("Type a command to begin. \nIf you need help type \"help\".");
    }
}