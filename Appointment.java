public class Appointment {
    private int appointmentId;
    private String patientId;
    private String doctorName;
    private String date;
    private String time;

    public Appointment(int appointmentId, String patientId,
                       String doctorName, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    public int getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorName() { return doctorName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public Appointment() {
    }
}
