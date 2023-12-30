import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class westminsterskinconsultationmanagerTest {
    @Test
    void addDoctor() {
        ArrayList<Doctor> doctors=westminsterskinconsultationmanager.getDoctors();
        doctors.add(new Doctor("Cosmetic skin specialist",31131,"Andrew","Tristan",767360276));
        assertEquals(1,doctors.size());
        for (int x=0;x<5;x++){
            doctors.add(new Doctor("Cosmetic skin specialist",31131,"Andrew","Tristan",767360276));
        }
        assertEquals(6,doctors.size());
    }

    @Test
    void deleteDoctor() {
        ArrayList<Doctor> doctors=westminsterskinconsultationmanager.getDoctors();
        //add two doctors
        doctors.add(new Doctor("Cosmetic skin specialist",31131,"Andrew","Tristan",767360276));
        doctors.add(new Doctor("dermatologist",56789,"Olatunji","Olajide",763974580));
        assertEquals(2,doctors.size());
        doctors.remove(0);
        //check if doctor was removed
        assertEquals(1,doctors.size());
        //verify doctor is removed
        assertEquals(56789,doctors.get(0).getDoc_licNumber());

    }

    @Test
    void saveDetails() {
        ArrayList<Doctor> doctors=westminsterskinconsultationmanager.getDoctors();
        int doccountemps=0;
        int checkagainst;
        for (int x=0;x<5;x++){
            doctors.add(new Doctor("Cosmetic skin specialist",31131,"Andrew","Tristan",767360276));
            doccountemps++;
        }
        File file = new File("doctortestdata.txt");
        try{
            FileWriter writeMe = new FileWriter(file);
            writeMe.write(doccountemps+"\n");
            for (Doctor doctor : doctors) {
                writeMe.write(doctor.getDoctor_name() + "\n");
                writeMe.write(doctor.getDoc_licNumber() + "\n");
                writeMe.write(doctor.getDoc_specialisation() + "\n");
                writeMe.write(doctor.getMobilenumber() + "\n");
            }
            writeMe.close();
            FileReader docread = new FileReader("doctortestdata.txt");
            Scanner myReader = new Scanner(docread);
            String doccounttemp = myReader.nextLine();
            checkagainst = Integer.parseInt(doccounttemp);
            assertEquals(5,checkagainst);
        }
        catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Test
    void printDoctor() {
    }

    @Test
    void readDetails() {
    }

    @Test
    void gui() {
    }
}