package com.hospital.repository.impl;

import java.io.File;
import com.hospital.repository.JsonSerializer;
import com.hospital.model.MedicationOrder;

public class MedicationOrderRepository {
    private final File orderDir;

    public MedicationOrderRepository(String basePath) {
        this.orderDir = new File(basePath, "orders");
        if (!orderDir.exists()) {
            orderDir.mkdirs();
        }
    }

    public void save(MedicationOrder mo) {
        File file = new File(orderDir, "ORD" + mo.getOrderId() + ".json");
        JsonSerializer.writeToFile(file, mo);
    }

    public MedicationOrder findById(String transactionId) {
        File file = new File(orderDir, transactionId + ".json");
        if (!file.exists())
            return null;
        return JsonSerializer.readFromFile(file, MedicationOrder.class);
    }
}
