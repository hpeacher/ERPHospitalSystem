package com.hospital.repository.impl;

import com.hospital.model.LabOrder;
import com.hospital.repository.ILabOrderRepository;
import com.hospital.repository.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LabOrderRepository implements ILabOrderRepository {
    private List<LabOrder> orders;
    private static int orderIdCounter = 0;
    private final File storageFile;
    private final Type listType = new TypeToken<List<LabOrder>>() {}.getType();

    public LabOrderRepository(String filePath) {
        this.storageFile = new File(filePath);
        this.orders = loadAll();
        initializeOrderIdCounter();
    }

    private List<LabOrder> loadAll() {
        if (!storageFile.exists()) {
            return new ArrayList<>();
        }
        return JsonSerializer.readListFromFile(storageFile, listType);
    }

    private void saveAll() {
        JsonSerializer.writeToFile(storageFile, orders);
    }

    private void initializeOrderIdCounter() {
        int maxId = 0;
        for (LabOrder order : orders) {
            String orderId = order.getOrderId();
            if (orderId != null && orderId.startsWith("LO")) {
                try {
                    int id = Integer.parseInt(orderId.substring(2));
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                    // Ignore malformed IDs
                }
            }
        }
        orderIdCounter = maxId;
    }

    @Override
    public void save(LabOrder order) {
        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            order.setOrderId("LO" + (++orderIdCounter));
        }
        orders.add(order);
        saveAll();
    }

    @Override
    public void update(LabOrder order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(order.getOrderId())) {
                orders.set(i, order);
                saveAll();
                return;
            }
        }
    }

    @Override
    public LabOrder findById(String orderId) {
        for (LabOrder order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<LabOrder> findByPatientId(String patientId) {
        return orders.stream()
                .filter(order -> order.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<LabOrder> findAllPending() {
        return orders.stream()
                .filter(order -> "Pending Lab Collection".equals(order.getStatus()) ||
                               "Results Ready for Review".equals(order.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LabOrder> findAll() {
        return new ArrayList<>(orders);
    }
}
