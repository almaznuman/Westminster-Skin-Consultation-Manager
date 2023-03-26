import java.util.Date;

public class consultation {
    /**
     * instance variables for consultation class
     */
    private int cost;
    private String doctor;
    private String patient;
    private Date date;
    private int timeslot;
    /**
     * relevant getters and setters for consultation instance variables
     */
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String notes;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * consultation constructor
     */
    public consultation(String doctor,String patient,Date date,int timeslot,String notes,int cost){
        this.doctor=doctor;
        this.patient=patient;
        this.date=date;
        this.timeslot=timeslot;
        this.notes=notes;
        this.cost=cost;
    }
}
