package com.hospital.repository.impl;

import com.hospital.model.LabOrder;
import com.hospital.repository.ILabOrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LabOrderRepository implements ILabOrderRepository {
    private List<LabOrder> orders;
    private static int orderIdCounter = 0;

    public LabOrderRepository() {
        this.orders = new ArrayList<>();
    }

    @Override
    public void save(LabOrder order) {
        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            order.setOrderId("LO" + (++orderIdCounter));
        }
        orders.add(order);
    }

    @Override
    public void update(LabOrder order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(order.getOrderId())) {
                orders.set(i, order);
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
