package com.hospital.service;

import com.hospital.model.*;
import com.hospital.repository.impl.InventoryRepository;
import com.hospital.repository.impl.MedicationOrderRepository;
import com.hospital.repository.impl.TransactionRepository;

import java.util.List;
import java.util.Optional;

public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final TransactionRepository transactionRepository;
    private final MedicationOrderRepository medicationOrderRepository;

    public InventoryService(InventoryRepository inventoryRepository,
            TransactionRepository transactionRepository, MedicationOrderRepository medicationOrderRepository) {
        this.inventoryRepository = inventoryRepository;
        this.transactionRepository = transactionRepository;
        this.medicationOrderRepository = medicationOrderRepository;
    }

    public List<MedicationStock> viewInventory() {
        return inventoryRepository.findAll();
    }

    public void withdrawMedication(String medicationId, int quantity) {
        Optional<MedicationStock> stockOpt = inventoryRepository.findByMedicationId(medicationId);

        if (stockOpt.isEmpty()) {
            throw new IllegalArgumentException("Medication not found in inventory");
        }

        MedicationStock stock = stockOpt.get();

        if (stock.getQuantityAvailable() < quantity) {
            throw new IllegalStateException("Insufficient stock for withdrawal");
        }

        // Deduct stock
        stock.setQuantityAvailable(stock.getQuantityAvailable() - quantity);
        inventoryRepository.save(stock);

        // Log transaction
        Transaction tx = new Transaction("WITHDRAWAL", medicationId, quantity);
        transactionRepository.save(tx);

    }

    public void updateStock(String medicationId, int quantityAdded) {
        Optional<MedicationStock> stockOpt = inventoryRepository.findByMedicationId(medicationId);
        if (stockOpt.isEmpty()) {
            throw new IllegalArgumentException("Medication not found in inventory.");
        }
        if (!isBelowThreshold(medicationId)) {
            throw new IllegalArgumentException("Reorder threshold not reached.");
        }

        MedicationStock stock = stockOpt.get();
        stock.setQuantityAvailable(stock.getQuantityAvailable() + quantityAdded);
        inventoryRepository.save(stock);

        MedicationOrder order = new MedicationOrder(medicationId, quantityAdded);
        medicationOrderRepository.save(order);

        Transaction tx = new Transaction("RESTOCK", medicationId, quantityAdded);
        transactionRepository.save(tx);
    }

    public void addMedication(String medicationId, String name, int initialQuantity, int reorderThreshold) {
        if (inventoryRepository.findByMedicationId(medicationId).isPresent()) {
            throw new IllegalStateException("Medication already exists in inventory");
        }

        MedicationStock stock = new MedicationStock(medicationId, name, initialQuantity, reorderThreshold);
        inventoryRepository.save(stock);

        Transaction tx = new Transaction("ADD_MEDICATION", medicationId, initialQuantity);
        transactionRepository.save(tx);
    }

    public boolean isBelowThreshold(String medicationId) {
        Optional<MedicationStock> stockOpt = inventoryRepository.findByMedicationId(medicationId);
        return stockOpt.map(stock -> stock.getQuantityAvailable() < stock.getReorderThreshold())
                .orElse(false);
    }
}
