import java.io.*;
import java.util.*;
public class westminsterskinconsultationmanager implements  skinconsultationmanager {
    private static ArrayList<Doctor>doctors=new ArrayList<>(10);
    File doctordata = new File("doctorData.txt");
    Scanner input = new Scanner(System.in);
    @Override
    public void addDoctor() {//creates doctor object
        try {
            if (Doctor.getDoctorcount() != 10) {
                int new_licnumber, newmobilenumber;
                String new_specialization, newname, newsurname;
                System.out.print("Enter Doctor's Name: ");
                newname = input.nextLine();
                while (!newname.matches("[a-zA-Z]+")) {
                    System.out.println("Enter a valid name");
                    newname = input.nextLine();
                }
                System.out.print("Enter Doctor's Surname: ");
                newsurname = input.nextLine();
                while (!newsurname.matches("[a-zA-Z]+")) {
                    System.out.println("Enter a valid name");
                    newsurname = input.nextLine();
                }
                int tempmobilenumber;
                while (true) {
                    try {
                        System.out.print("Enter Mobile Number: ");
                        tempmobilenumber = input.nextInt();
                        while ((String.valueOf(tempmobilenumber).length()) !=9 ) {
                            System.out.println("Number should be 10 digits, try entering a valid number");
                            while (!input.hasNextInt()) {
                                System.out.println("Input is not a number, try inputting a valid number");
                                input.next();
                            }
                            tempmobilenumber = input.nextInt();
                        }
                        for(int x=0; x<doctors.size();x++){
                            if(doctors.get(x).getMobilenumber()==tempmobilenumber){
                                System.out.println("Mobile Number is registered to another doctor, please enter a different Mobile number"+"\n");
                                tempmobilenumber=0;
                                break;
                            }
                            else if (x==(doctors.size()-1)){
                                break;
                            }
                        }
                        if (tempmobilenumber==0){
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input . Please enter a valid mobile number");
                        input.nextLine();
                    }
                }
                newmobilenumber=tempmobilenumber;
                int temp1;
                while (true) {
                    try {
                        System.out.print("Enter License Number: ");
                        temp1 = input.nextInt();
                        while ((String.valueOf(temp1).length()) !=5 ) {
                            System.out.println("Number should be 5 digits, try entering a valid number");
                            while (!input.hasNextInt()) {
                                System.out.println("Input is not a number, try inputting a valid number");
                                input.next();
                            }
                            temp1 = input.nextInt();
                        }
                        for(int x=0; x<doctors.size();x++){
                            if(doctors.get(x).getDoc_licNumber()==temp1){
                                System.out.println("License Number is registered to another doctor, please enter a different license number"+"\n");
                                temp1 = 0;
                                break;
                            }
                            else if (x==(doctors.size()-1)){
                                break;
                            }
                        }
                        if (temp1==0){
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input . Please enter a valid mobile number");
                        input.nextLine();
                    }
                }
                new_licnumber=temp1;
                System.out.print("Enter Specialization: ");
                new_specialization = input.nextLine();
                while (!new_specialization.matches("[a-zA-Z\s]+")) {
                    System.out.println("Specialization should only contain letters");
                    new_specialization = input.nextLine();
                }
                doctors.add(new Doctor(new_specialization, new_licnumber, newname, newsurname, newmobilenumber));
            } else {
                System.out.println("Center is at maximum allocation");
            }
        }catch (InputMismatchException e){
            System.out.println("Error, try again");
            e.printStackTrace();
        }
    }
    @Override
    public void deleteDoctor () {//finds and deletes doctor selected by user
        int deleteliscensenum;
        System.out.print("Enter License Number: ");
        try {
            while (!input.hasNextInt()) {
                System.out.println("Input is not a number, try inputting a valid number");
                input.nextLine();
            }
            deleteliscensenum = Integer.parseInt(input.nextLine());
            for (int x = 0; x < doctors.size(); x++) {
                if (doctors.get(x).getDoc_licNumber() == deleteliscensenum) {
                    System.out.println("");
                    System.out.println("Doctor deleted- " + doctors.get(x).getDoctor_name());
                    System.out.println("License number- " + doctors.get(x).getDoc_licNumber());
                    System.out.println("Specialization- " + doctors.get(x).getDoc_specialisation());
                    System.out.println("Mobile Number- " + doctors.get(x).getMobilenumber());
                    doctors.remove(x);
                    Doctor.setDoctorcount(Doctor.getDoctorcount() - 1);
                    System.out.println("Center Doctor Count- " + Doctor.getDoctorcount());
                    break;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Error, try again");

        }
    }
    @Override
    public void saveDetails(){//saves doctor data in a textfile
        String doccountemps=Integer.toString(Doctor.getDoctorcount());
        try{
            FileWriter writeMe = new FileWriter(doctordata);
            writeMe.write(doccountemps+"\n");
            for (int x= 0;x<doctors.size();x++){
                writeMe.write(doctors.get(x).getDoctor_name()+"\n");
                writeMe.write(doctors.get(x).getDoc_licNumber()+"\n");
                writeMe.write(doctors.get(x).getDoc_specialisation()+"\n");
                writeMe.write(doctors.get(x).getMobilenumber()+"\n");
            }
            writeMe.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    @Override//count
    public void printDoctor () {//prints out sorted doctors according to their surname
        ArrayList<Doctor>doctortemp;
        doctortemp=(ArrayList)doctors.clone();
        Collections.sort(doctortemp,Doctor.DocNameComparator);
        for (int x = 0; x<doctortemp.size(); x++){
            System.out.println("Doctor "+(x+1)+"\n");
            System.out.println("Doctor Name- "+doctortemp.get(x).getDoctor_name());
            System.out.println("License number- "+doctortemp.get(x).getDoc_licNumber());
            System.out.println("Specialization- "+doctortemp.get(x).getDoc_specialisation());
            System.out.println("Mobile Number- "+doctortemp.get(x).getMobilenumber());
            System.out.println("\n");
        }
    }
    @Override
    public void readDetails(){//reads details from text file
        boolean exists = doctordata.exists();
        if(exists==true) {
            try {
                String[] firstandsurname;
                String fname, surname, mobilenumbertemp, nametemp, specialtemp, licnumtemp, doccounttemp;
                FileReader docread = new FileReader(doctordata);
                Scanner myReader = new Scanner(docread);
                doccounttemp = myReader.nextLine();
                int doccounttemp1 = Integer.parseInt(doccounttemp);
                for (int x = 0; x < doccounttemp1; x++) {
                    nametemp = myReader.nextLine();
                    firstandsurname=nametemp.split(" ",2);//seperates into two strings where there is space
                    fname=firstandsurname[0];
                    surname=firstandsurname[1];
                    licnumtemp = myReader.nextLine();
                    int licnumtemp1 = Integer.parseInt(licnumtemp);
                    specialtemp = myReader.nextLine();
                    mobilenumbertemp = myReader.nextLine();
                    int mobilenumbertemp1 = Integer.parseInt(mobilenumbertemp);
                    doctors.add(new Doctor(specialtemp, licnumtemp1,fname,surname,mobilenumbertemp1));
                }
                myReader.close();
            } catch (IOException e) {
                System.out.println("Error occurred");
                e.printStackTrace();
            }
        }
    }
    @Override
    public void gui() {//creates gui object
        String name= "Doctor Schedule";
        gui test= new gui(name);
    }


    public static ArrayList<Doctor> getDoctors() {
        return doctors;
    }
}