package com.hospital.command.impl;

import java.util.Scanner;
import java.util.List;

import com.hospital.command.ICommand;
import com.hospital.service.InventoryService;
import com.hospital.model.MedicationStock;

public class InventoryCommand implements ICommand {
    private Scanner sc;
    private InventoryService invService;

    public InventoryCommand(Scanner scanner, InventoryService inventoryService) {
        this.sc = scanner;
        this.invService = inventoryService;
    }

    @Override
    public String getToken() {
        return "manage_inventory";
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("\n=== Inventory Manager ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Order More");
            System.out.println("3. Withdraw from Inventory");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");

            String choice = sc.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    viewInventory();
                    break;
                case "2":
                    orderMore();
                    break;
                case "3":
                    withdrawFromInventory();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewInventory() {
        List<MedicationStock> stock = invService.viewInventory();
        if (stock == null || stock.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }
        for (MedicationStock s : stock) {
            System.out.println(s);
        }
    }

    private void orderMore() {
        System.out.println("Will be finished in another branch");
    }

    private void withdrawFromInventory() {
        System.out.println("Enter medication id: ");
        String id = sc.nextLine().trim();
        System.out.println("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        try {
            invService.withdrawMedication(id, quantity);
            System.out.println("Successfully withdrew " + quantity + " of medicine " + id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
