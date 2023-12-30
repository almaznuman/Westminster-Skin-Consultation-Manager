import java.util.Comparator;

class Doctor extends person {

    /**
     * instance variables to store doctor's details
     */
    private static int doctorcount = 0;//static doctor count for printing purposes
    private String doctor_name;
    private String doc_specialisation;
    private int doc_licNumber;

    public Doctor(String specialisation, int doc_licNumber, String name, String surname, int mobilenumumber) {//doctor
        super(name, surname, mobilenumumber);
        this.doctor_name = name + " " + surname;
        this.doc_specialisation = specialisation;
        this.doc_licNumber = doc_licNumber;
        doctorcount++;
    }
    //sorting doctor's last names in alphabetic ascending order
    /**
     * method to sort doctors in alphabetical order according to their surnames
     */
    public static Comparator<Doctor> DocNameComparator = (d1, d2) -> {
        String docname = d1.getSurname().toUpperCase();
        String docname2 = d2.getSurname().toUpperCase();
        return docname.compareTo(docname2);
    };

    /**
     * relevant getters and setters for doctors instance variables
     */
    public static int getDoctorcount() {//returns doctor count
        return doctorcount;
    }

    public static void setDoctorcount(int doctorcount) {
        Doctor.doctorcount = doctorcount;
    }//sets doctor count

    public String getDoctor_name() {
        return doctor_name;
    }//returns doctor name

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }//sets doctor name

    public String getDoc_specialisation() {
        return doc_specialisation;
    }//returns doctor specialization

    public void setDoc_specialisation(String doc_specialisation) {
        this.doc_specialisation = doc_specialisation;
    }//sets doctor specialization

    public int getDoc_licNumber() {
        return doc_licNumber;
    }//returns doctor license number

    public void setDoc_licNumber(int doc_licNumber) {
        this.doc_licNumber = doc_licNumber;
    }//sets doctor license number
}