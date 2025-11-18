package com.hospital.model;

/**
 * Represents a discharge checklist for a patient. No functions, just data.
 */
public class DischargeChecklist {
    private boolean finalized;
    private boolean notesCompleted;
    private String signature;

    public DischargeChecklist() {
        this.finalized = false;
        this.notesCompleted = false;
        this.signature = null;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public boolean isNotesCompleted() {
        return notesCompleted;
    }

    public void setNotesCompleted(boolean notesCompleted) {
        this.notesCompleted = notesCompleted;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
