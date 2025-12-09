package com.hospital.repository;

import com.hospital.model.LabOrder;
import java.util.List;

public interface ILabOrderRepository {
    void save(LabOrder order);
    void update(LabOrder order);
    LabOrder findById(String orderId);
    List<LabOrder> findByPatientId(String patientId);
    List<LabOrder> findAllPending();
    List<LabOrder> findAll();
}
