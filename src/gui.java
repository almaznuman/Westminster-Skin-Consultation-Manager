import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class gui extends JFrame {// decide where to have the arrays so that data isn't erased after gui is closed not complete and notes encryption
    private SimpleDateFormat dateofbirthformatter = new SimpleDateFormat("dd/MM/yyyy");//passes string entered in (dd/mm/yyyy) to create a date object
    File patientdata = new File("patientdata.txt");
    Cipher cipher;
    JPanel viewconsultationbuttonpannel=new JPanel(new GridLayout(2,1));
    File file = new File("patientBooked.txt");
    JPanel mainpanel= new JPanel(new GridLayout(4,1));
    JButton viewconsultationdetails= new JButton("View Consultation");
    JButton exit = new JButton("Exit");
    Random random=new Random();
    private int doctorselected;
    private int dateselected;
    private int timeselected;
    private int patientNIC=0;
    private String patient_name;
    private String patient_surname;
    private String patient_DOB;
    private int patient_mobile_number = 0;
    private ArrayList<consultation>consultations=new ArrayList<>();
    private ArrayList<Doctor> docprint =westminsterskinconsultationmanager.getDoctors();
    private static ArrayList <patient> patients= new ArrayList<>();
    private String[][] data = new String [Doctor.getDoctorcount()][5];
    private static String[][][]arrays = new String[Doctor.getDoctorcount()][31][10];
    JButton selectdocotrbutton= new JButton("Select");
    public void setNotes(String notes) {
        this.notes = notes;
    }
    private static String notes;

    private  int cost;
    String key = "2795898326930176";
    public gui(String name){
        super(name);
        coverpage();
        readDetails();
    }
    /**
     * method that creates and displays the cover page
     */
    public void coverpage(){
        JFrame coverpage = new JFrame();
        coverpage.setLocation(550,100);
        JPanel flowbutn=new JPanel(new FlowLayout());
        JPanel panel=new JPanel(new GridLayout(2,1));
        JButton getstartedbutton=new JButton("Get Started");
        getstartedbutton.setPreferredSize(new Dimension(100,20));
        getstartedbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                mainpage();
                coverpage.setVisible(false);
                coverpage.dispose();
            }
        });
        flowbutn.add(getstartedbutton);
        JLabel label=new JLabel("Welcome to Westminster Skin Consultation Clinic");
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        flowbutn.setAlignmentX(0.5f);
        panel.add(label);
        panel.add(flowbutn);
        coverpage.add(panel);
        coverpage.setSize(500,500);
        coverpage.setAlwaysOnTop(true);
        coverpage.setVisible(true);
        coverpage.setResizable(false);
    }
    /**
     * method that creates and displays the main page
     */
    public void mainpage(){
        JPanel introgridpannel=new JPanel(new GridLayout(2,1));
        JPanel intropage2= new JPanel(new FlowLayout());
        JPanel intropage = new JPanel(new FlowLayout());
        JPanel tablepanel = new JPanel(new FlowLayout());
        if (file.exists()){//s
            try {
                FileReader docread = new FileReader(file);
                Scanner scanner = new Scanner(docread);
                for (int i = 0; i < arrays.length; i++) {
                    for (int j = 0; j < arrays[i].length; j++) {
                        for (int k = 0; k < arrays[i][j].length; k++) {
                            arrays[i][j][k] = scanner.next();
                        }
                    }
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            for (int i = 0; i < arrays.length; i++) {
                for (int j = 0; j < arrays[i].length; j++) {
                    for(int l=0;l<arrays[i][l].length;l++) {
                        arrays[i][j][l] = "YES";
                    }
                }
            }
        }
        String[] columnNames = {"Index","Doctor Name", "Specialization","License Number", "Mobile Number"};
        int indexcount=1;
        for (int i = 0; i < docprint.size(); i++) {
            data[i][0]= String.valueOf(indexcount);
            indexcount++;
            data[i][1] = docprint.get(i).getDoctor_name();
            data[i][2] = docprint.get(i).getDoc_specialisation();
            data[i][3]= String.valueOf(docprint.get(i).getDoc_licNumber());
            data[i][4]=String.valueOf(docprint.get(i).getMobilenumber());
        }

        JTable table = new JTable(data,columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(480,100));
        JLabel selectdoctor= new JLabel("Select doctor by index (1 - "+Doctor.getDoctorcount()+"): ");
        JTextField inputtextfield= new JTextField("");
        inputtextfield.setPreferredSize(new Dimension(100,30));
        selectdocotrbutton.setPreferredSize(new Dimension(100,20));
        JLabel date=new JLabel("Date- ");
        JTextField datetext= new JTextField("");
        JLabel timeslot= new JLabel("Time-Slot");
        JTextField timeslottext= new JTextField("");
        JButton FAQ= new JButton("FAQ");
        FAQ.setPreferredSize(new Dimension(100,20));
        FAQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Enter Doctor as per the relevant doctor index"+"\n"+"Enter a date from 1-31"+"\n"+"Enter timeslots in the following format-  \"6.00 AM\" in 24 hour format"+"\n"+"NIC should consist of 6 digits");
            }
        });
        JButton sortdocotr= new JButton("Sort doctors");
        sortdocotr.setPreferredSize(new Dimension(130,20));
        sortdocotr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexcount=1;
                Collections.sort(docprint,Doctor.DocNameComparator);//sorts doctor by last name
                for (int i = 0; i < docprint.size(); i++) {
                    data[i][0]= String.valueOf(indexcount);
                    indexcount++;
                    data[i][1] = docprint.get(i).getDoctor_name();
                    data[i][2] = docprint.get(i).getDoc_specialisation();
                    data[i][3]= String.valueOf(docprint.get(i).getDoc_licNumber());
                    data[i][4]=String.valueOf(docprint.get(i).getMobilenumber());
                }
                DefaultTableModel newModel = new DefaultTableModel(data, columnNames);
                table.setModel(newModel);
                intropage.revalidate();
                intropage.repaint();
            }
        });
        selectdocotrbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int tempdate;
                    int input;
                    input = Integer.parseInt(inputtextfield.getText());
                    tempdate=Integer.parseInt(datetext.getText());
                    int temptime=Integer.parseInt((timeslottext.getText()).split(":")[0]);
                    if (tempdate<1 || tempdate>31){
                        JOptionPane.showMessageDialog(null, "Invalid date. Please enter a date between 1-31");
                    }else if(input<1 || input >Doctor.getDoctorcount()){
                        JOptionPane.showMessageDialog(null, "Invalid Doctor input. Please enter a number between 1 and " + Doctor.getDoctorcount()+": ");
                    }else if (temptime<9||temptime>18){
                        JOptionPane.showMessageDialog(null, "Invalid Time-Slot input. Please enter a time between 9:00 AM and 18:00 PM");
                    }
                    else {
                        dateselected=tempdate;
                        timeselected=temptime;
                        input--;
                        doctorselected=input;
                        tempdate--;
                        temptime=temptime-9;
                        if (!fullybooked(input, tempdate, temptime)) {
                            //error label and assign new random doctor
                            int randomdoctorindex= random.nextInt((Doctor.getDoctorcount()) + 1);
                            // if the generated number is the excluded number, generate another one
                            while (randomdoctorindex == input) {
                                randomdoctorindex = random.nextInt((Doctor.getDoctorcount()) + 1);
                            }
                            JOptionPane.showMessageDialog(null, docprint.get(input).getDoctor_name()+" is currently fully booked, you are assigned to "+ docprint.get(randomdoctorindex).getDoctor_name());
                            arrays[randomdoctorindex][tempdate][temptime]="NO";
                            try {// stores the entire bookedarray into a textfile for future use
                                BufferedWriter writer = new BufferedWriter(new FileWriter(gui.this.file));
                                for (int i = 0; i < arrays.length; i++) {
                                    for (int j = 0; j < arrays[i].length; j++) {
                                        for (int k = 0; k < arrays[i][j].length; k++) {
                                            writer.write(arrays[i][j][k] + " ");
                                        }
                                        writer.newLine();
                                    }
                                    writer.newLine();
                                }
                                writer.close();
                            } catch (IOException l) {
                                l.printStackTrace();
                            }
                            doctorschedule(docprint.get(randomdoctorindex).getDoctor_name(), randomdoctorindex,tempdate);
                            setVisible(false);

                        }else {
                            arrays[input][tempdate][temptime]="NO";
                            try {// stores the entire bookedarray into a textfile for future use
                                BufferedWriter writer = new BufferedWriter(new FileWriter(gui.this.file));
                                for (int i = 0; i < arrays.length; i++) {
                                    for (int j = 0; j < arrays[i].length; j++) {
                                        for (int k = 0; k < arrays[i][j].length; k++) {
                                            writer.write(arrays[i][j][k] + " ");
                                        }
                                        writer.newLine();
                                    }
                                    writer.newLine();
                                }
                                writer.close();
                            } catch (IOException l) {
                                l.printStackTrace();
                            }
                            doctorschedule(docprint.get(input).getDoctor_name(), input,tempdate);
                            setVisible(false);
                        }
                        inputtextfield.setText("");
                        datetext.setText("");
                        timeslottext.setText("");
                    }
                } catch (Exception a) {
                    JOptionPane.showMessageDialog(null, "Please enter appropriate data in their appropriate fields (Click FAQ for more information)");
                }
            }
        });
        datetext.setPreferredSize(new Dimension(100,30));
        timeslottext.setPreferredSize(new Dimension(100,30));
        tablepanel.add(scrollPane);
        intropage.add(selectdoctor);
        intropage.add(inputtextfield);
        intropage.add(date);
        intropage.add(datetext);
        intropage.add(timeslot);
        intropage.add(timeslottext);
        intropage2.add(selectdocotrbutton);
        intropage2.add(sortdocotr);
        intropage2.add(FAQ);
        introgridpannel.add(intropage);
        introgridpannel.add(intropage2);
        mainpanel.add(tablepanel);
        mainpanel.add(introgridpannel);
        add(mainpanel);
        setSize(800,850);
        setResizable(false);
        exit.setPreferredSize(new Dimension(150,50));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        viewconsultationdetails.setPreferredSize(new Dimension(150,50));
        viewconsultationdetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String decryptedText;
                try {
                    cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(notes));
                    decryptedText = new String(decrypted, StandardCharsets.UTF_8);

                } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                         BadPaddingException | InvalidKeyException a) {
                    throw new RuntimeException(a);
                }
                JTextArea details= new JTextArea();
                details.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                details.setPreferredSize(new Dimension(500,80));
                details.setEditable(false);
                String[]text1={"Doctor name- "+ docprint.get(doctorselected).getDoctor_name(),"Patient name- "+patient_name+" "+patient_surname,"Date- "+dateselected+"th of january","Time- "+timeselected+":00"+" Notes- "+decryptedText,"Cost- "+cost};
                for (String line : text1) {
                    details.append(line + "\n");
                }
                JPanel textareapanel=new JPanel(new FlowLayout());
                textareapanel.add(details);
                viewconsultationbuttonpannel.add(textareapanel);
                viewconsultationbuttonpannel.revalidate();
                viewconsultationbuttonpannel.repaint();
                viewconsultationdetails.setEnabled(false);
            }
        });
    }
    /**
     * method that creates and displays the 3rd frame, where patient enters their details
     */
    public void doctorschedule(String docname, int a, int b){
        final String[] timeslots = {"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "13:00 PM", "14:00 PM", "15:00 PM", "16:00 PM", "17:00 PM", "18:00 PM"};
        final String []columnnames={"Time-Slot","Available"};
        JFrame newFrame = new JFrame(docname+"'s Schedule");
        newFrame.setSize(700, 750);
        newFrame.setLayout(new BorderLayout());
        Object[][] data = new Object[timeslots.length][3];
        for (int x=0; x<timeslots.length;x++){
            data[x][0] = timeslots[x];
            data[x][1]= arrays[a][b][x];
        }
        JTable table= new JTable(data,columnnames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table.setPreferredScrollableViewportSize(new Dimension(500,160));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        JPanel localpanel=new JPanel(new BorderLayout());
        JPanel panel=new JPanel(new FlowLayout());
        JPanel panel2= new JPanel(new FlowLayout());
        JPanel panel3= new JPanel(new FlowLayout());
        JPanel panel4=new JPanel(new FlowLayout());
        JPanel notespanel=new JPanel(new FlowLayout());

        JLabel nametext = new JLabel("Name- ");
        JLabel surnametext = new JLabel("Surname- ");
        JLabel NIC= new JLabel("NIC- ");
        JLabel dateofbith=new JLabel("Date Of Birth- ");
        JLabel mobile_number=new JLabel("Mobile Number- ");


        JTextField name= new JTextField();
        name.setPreferredSize(new Dimension(100,30));
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patient_nametemp=name.getText();
                if (!patient_nametemp.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "First name should only contain text");
                }
            }
        });
        JTextField surname= new JTextField();
        surname.setPreferredSize(new Dimension(100,30));
        surname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patient_surnametemp=surname.getText();
                if (!patient_surnametemp.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Surname should only contain text");
                }
            }
        });
        JTextField nictext= new JTextField();
        nictext.setPreferredSize(new Dimension(100,30));
        nictext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nictemp= nictext.getText();
                if(!nictemp.matches("^[0-9]+$")||(nictemp.length()!=6)){
                    JOptionPane.showMessageDialog(null, "NIC number is not valid, try entering it again");
                }
            }
        });
        JTextField mobileno= new JTextField();
        mobileno.setPreferredSize(new Dimension(100,30));
        mobileno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mobilenumtemp=mobileno.getText();
                if(mobilenumtemp.length()!=10){
                    JOptionPane.showMessageDialog(null, "Mobile number should contain 10 digits");
                }
            }
        });
        JTextField dob= new JTextField();
        dob.setPreferredSize(new Dimension(100,30));
        dob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patient_DOBtemp=dob.getText();
                if (!isValidDOB(patient_DOBtemp)){
                    JOptionPane.showMessageDialog(null, "Date of birth should be entered in this format with slashes (dd/mm/yyyy)");
                }
            }
        });
        JButton button1= new JButton("Create Consultation");
        button1.setPreferredSize(new Dimension(150,20));
        JLabel patientdetails= new JLabel("Enter Patient Details");
        patientdetails.setHorizontalAlignment(JLabel.CENTER);
        JLabel Noteslabel= new JLabel("Notes");
        Border border =BorderFactory.createLineBorder(Color.black);
        JTextArea NotesTextarea= new JTextArea();
        NotesTextarea.setPreferredSize(new Dimension(200,50));
        NotesTextarea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String notestemp=NotesTextarea.getText();
                if (!notestemp.matches("^[a-zA-Z ]*$")) {
                    JOptionPane.showMessageDialog(null, "Notes should only contain text");
                }
            }
        });
        NotesTextarea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {//create object after all inputs are validated
                    patientNIC=Integer.parseInt(nictext.getText());
                    patient_mobile_number=Integer.parseInt(mobileno.getText());
                    patient_name=name.getText();
                    patient_surname=surname.getText();
                    patient_DOB=dob.getText();
                    setNotes(NotesTextarea.getText());
                    JOptionPane.showMessageDialog(null, "Successfully created consultation, this window will close in 2 seconds");
                    patients.add(new patient(patientNIC,patient_name,patient_surname,patient_DOB,patient_mobile_number));
                    Thread.sleep(2000);
                    if (patients.size()==1){
                        cost=15;
                    }else {
                        for (int x = 0;x<patients.size()-1;x++){
                            if (patients.get(x).getPatientID()==patientNIC){
                                cost=25;
                            }else{
                                cost=15;
                            }
                        }
                    }
                    try {
                        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                        byte[] encrypted = cipher.doFinal(notes.getBytes(StandardCharsets.UTF_8));
                        notes = Base64.getEncoder().encodeToString(encrypted);

                    } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                             BadPaddingException | InvalidKeyException a) {
                        throw new RuntimeException(a);
                    }
                    consultations.add(new consultation(docname,patient_name+" "+patient_surname,dateofbirthformatter.parse(patient_DOB),timeselected,notes,cost));
                    saveDetails();
                }catch (Exception a){
                    System.out.println("error");
                }
                newFrame.setVisible(false);
                newFrame.dispose();
                JPanel flow=new JPanel(new FlowLayout());
                flow.add(viewconsultationdetails);
                flow.add(exit);
                viewconsultationbuttonpannel.add(flow);
                mainpanel.add(viewconsultationbuttonpannel);
                mainpanel.revalidate();
                mainpanel.repaint();
                setVisible(true);
                selectdocotrbutton.setEnabled(false);
            }
        });
        panel3.add(nametext);
        panel3.add(name);
        panel3.add(surnametext);
        panel3.add(surname);
        panel3.add(NIC);
        panel3.add(nictext);
        panel4.add(dateofbith);
        panel4.add(dob);
        panel4.add(mobile_number);
        panel4.add(mobileno);
        notespanel.add(Noteslabel);
        notespanel.add(NotesTextarea);
        JPanel panel5= new JPanel(new GridLayout(5,1,0,20));
        panel5.add(patientdetails);
        panel5.add(panel3);
        panel5.add(panel4);
        panel5.add(notespanel);
        panel5.add(button1);

        JLabel doctorheading = new JLabel("Dr. "+docname+"'s Schedule");
        doctorheading.setFont(new Font(doctorheading.getFont().getName(), doctorheading.getFont().getStyle(), 20));
        panel2.add(doctorheading);
        JScrollPane scrollPane= new JScrollPane(table);
        panel.add(scrollPane);
        localpanel.add(panel2,BorderLayout.NORTH);
        localpanel.add(panel,BorderLayout.CENTER);
        newFrame.add(localpanel);
        newFrame.add(panel5,BorderLayout.PAGE_END);
        newFrame.setVisible(true);
        newFrame.setResizable(false);
    }
    public boolean fullybooked(int input, int dateselected,int timeslot){//checks whether the time slot on a certain day is booked
        boolean a = !arrays[input][dateselected][timeslot].equals("NO");
        return a;
    }
    public boolean isValidDOB(String dobString) {//checks whether date of birth assigned is valid
        String[] dobParts = dobString.split("/");
        int day = Integer.parseInt(dobParts[0]);
        int month = Integer.parseInt(dobParts[1]);
        int year = Integer.parseInt(dobParts[2]);

        if (day< 1|| day>31) {
            return false;
        }
        if (month<1||month>12) {
            return false;
        }
        if (year<1900||year>Calendar.getInstance().get(Calendar.YEAR)) {
            return false;
        }
        return true;
    }
    public void saveDetails(){//save details to file
        String doccountemps=Integer.toString(patient.getPatientcount());
        try{
            FileWriter writeMe = new FileWriter(patientdata);
            writeMe.write(doccountemps+"\n");
            String datewrite;
            for (int x= 0;x<patients.size();x++){
                datewrite=dateofbirthformatter.format(patients.get(x).getDateofbirth());
                writeMe.write(patients.get(x).getPatientID()+"\n");
                writeMe.write(patients.get(x).getPatientname()+"\n");
                writeMe.write(datewrite+"\n");
                writeMe.write(patients.get(x).getMobilenumber()+"\n");
            }
            writeMe.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void readDetails(){//method to get previous patient details
        boolean exists = patientdata.exists();
        if(exists==true) {
            try {
                String[] firstandsurname;
                String fname, surname, mobilenumbertemp, nametemp, dateofbirth, patientcount;
                int patientid;
                FileReader docread = new FileReader(patientdata);
                Scanner myReader = new Scanner(docread);
                patientcount = myReader.nextLine();
                int patientcount1 = Integer.parseInt(patientcount);
                for (int x = 0; x < patientcount1; x++) {
                    patientid=Integer.parseInt(myReader.nextLine());
                    nametemp = myReader.nextLine();
                    firstandsurname=nametemp.split(" ",2);//seperates into two strings where there is space
                    fname=firstandsurname[0];
                    surname=firstandsurname[1];
                    dateofbirth = myReader.nextLine();
                    mobilenumbertemp = myReader.nextLine();
                    int mobilenumbertemp1 = Integer.parseInt(mobilenumbertemp);
                    patients.add(new patient(patientid,fname,surname,dateofbirth,mobilenumbertemp1));
                }
                myReader.close();
            } catch (IOException e) {
                System.out.println("Error occurred");
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
