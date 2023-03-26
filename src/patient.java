import java.text.ParseException;

public class patient extends person{
    /**
     *  instance variables to store patient details
     */
    private  String patientname;
    private int patientID;
    private static int patientcount;
    /**
     *  relevant getters and setters for patient instance variables
     */
    public static int getPatientcount() {
        return patientcount;
    }
    public int getPatientID() {
        return patientID;
    }//gets patient id

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }//sets patient id
    public String getPatientname() {
        return patientname;
    }//returns patient name

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }//sets patient name

    public patient(int patientID, String name, String surname, String dateofbirth, int mobilenumber) throws ParseException {//patient constructor
        super(name, surname, dateofbirth, mobilenumber);
        this.patientname= name+" "+surname;
        this.patientID=patientID;
        patientcount++;
    }


}
