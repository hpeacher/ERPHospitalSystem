package com.hospital.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hospital.repository.JsonSerializer;
import com.hospital.model.MedicationStock;

public class InventoryRepository {
    private final File inventoryDir;

    public InventoryRepository(String basePath) {
        this.inventoryDir = new File(basePath, "inventory");
        if (!inventoryDir.exists()) {
            inventoryDir.mkdirs();
        }
    }

    public void save(MedicationStock stock) {
        File file = new File(inventoryDir, stock.getMedicationId() + ".json");
        JsonSerializer.writeToFile(file, stock);
    }

    public Optional<MedicationStock> findByMedicationId(String medicationId) {
        
        File file = new File(inventoryDir, medicationId + ".json");

        if (!file.exists())
            return Optional.empty();
        return Optional.of(JsonSerializer.readFromFile(file, MedicationStock.class));
    }

    public List<MedicationStock> findAll() {
        List<MedicationStock> stocks = new ArrayList<>();
        File[] files = inventoryDir.listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try {
                    MedicationStock stock = JsonSerializer.readFromFile(file, MedicationStock.class);
                    stocks.add(stock);
                } catch (RuntimeException e) {
                    System.err.println("Skipping invalid file: " + file.getName());
                }
            }
        }
        return stocks;
    }
}
