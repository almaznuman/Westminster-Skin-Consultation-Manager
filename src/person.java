import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

abstract class person {
    /**
     * instance variables to store person details
     */
    private String name;
    private String surname;
    private Date dateofbirth;
    private int mobilenumber;
    private SimpleDateFormat dateofbirthformatter = new SimpleDateFormat("dd/MM/yyyy");//to pass date in the format of string into a date object

    public person(String name, String surname, String dateofbirth, int mobilenumber) throws ParseException {
        this.name = name;
        this.surname = surname;
        this.dateofbirth=dateofbirthformatter.parse(dateofbirth);
        this.mobilenumber = mobilenumber;
    }
    public person(String name, String surname,int mobilenumber) {
        this.name = name;
        this.surname = surname;
        this.mobilenumber = mobilenumber;
    }
/**
* relevant getters and setters for patient instance variables
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public int getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(int mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}
