interface ICheckListProcessor {
    void initiateChecklist(VisitRecord visitRecord);

    boolean completeChecklist(VisitRecord visitRecord, String notes, String signature);
}

public class ChecklistProcessor implements ICheckListProcessor {

    private DischargeManager dischargeManager;

    public ChecklistProcessor() {
        dischargeManager = DischargeManager.getInstance();
    }

    public void initiateChecklist(VisitRecord visitRecord) {
        // Implementation for initiating the checklist
        visitRecord.setDischargeChecklist(new DischargeChecklist());
    }

    public boolean completeChecklist(VisitRecord visitRecord, String notes, String signature) {
        // Implementation for completing the checklist
        visitRecord.setNotes(notes);
        DischargeChecklist checklist = visitRecord.getDischargeChecklist();
        if (checklist != null) {
            checklist.setNotesCompleted(true);
            checklist.setSignature(signature);
            dischargeManager.checklistCompleted(visitRecord);
            return true;
        }
        return false;
    }
}