import java.util.Scanner;

public class Driverclass {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        westminsterskinconsultationmanager driver = new westminsterskinconsultationmanager();// new object creation of westmisnterconsultationclass obj
        System.out.println("Welcome to Westminster Skin Consultation"+"\n");// menu options displayed in the console menu

        driver.readDetails();//creates previous instances of doctor objected, where their data was stored into a file
        while(true) {
            System.out.println("------------------------------------------------");
            System.out.println();
            System.out.println("1: Add a New Doctor");
            System.out.println("2: Delete a Doctor");
            System.out.println("3: Store program data");
            System.out.println("4: Print Doctor Details");
            System.out.println("5: GUI");
            System.out.println("0: Exit");
            System.out.println(" ");
            System.out.println("\n"+"Enter Choice");
            String a = input.nextLine();
            switch (a) {
                case "1" -> driver.addDoctor();
                case "2" -> driver.deleteDoctor();
                case "3" -> driver.saveDetails();
                case "4" -> driver.printDoctor();
                case "5" -> driver.gui();
                case "0" -> System.exit(0);
                default -> System.out.println("Incorrect Entry, Try again");
            }
        }
    }
}

