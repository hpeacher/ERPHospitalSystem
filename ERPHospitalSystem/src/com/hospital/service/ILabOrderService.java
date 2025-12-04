package com.hospital.service;

import com.hospital.model.LabOrder;
import java.util.List;

public interface ILabOrderService {
    LabOrder createOrder(String patientId, String doctorId, String testName);
    boolean submitResults(String orderId, String results, String notes);
    boolean approveOrder(String orderId);
    LabOrder getOrderById(String orderId);
    List<LabOrder> getPendingOrders();
    List<LabOrder> getPendingOrdersForReview();
    boolean cancelOrder(String orderId);
    boolean validateCriticalResults(String results);
}
