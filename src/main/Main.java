package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static ServerSocket server;
    static Socket receptions;
    static DataOutputStream outputStream;
    static List<Integer> deletedPatientsIds= new ArrayList<>();
    static DataInputStream inputStream;
    static String yesOrNo;
    static List<Doctor> doctors = new ArrayList<Doctor>();
    static List<Patient> patients = new ArrayList<Patient>();
    static List<Appointment> appointments = new ArrayList<Appointment>();

    public static void addFakeData(){
        Week week = new WeekBuilder(LocalTime.of(8,30),LocalTime.of(16,30)).setSun(true).setMon(true).setTue(true).build();
        Doctor doctor = new Doctor("D.Hala",week,1);
        doctors.add(doctor);
        Patient patient = new Patient("patient:hamza",1,432,"dsfdsf");
        patients.add(patient);
    }
    public static void main(String[] args) {

        try {
            server = new ServerSocket(5000);
            receptions = server.accept();
            outputStream = new DataOutputStream(receptions.getOutputStream());
            inputStream = new DataInputStream(receptions.getInputStream());
            yesOrNo = "";
            /*
            receiveDoctor();
            receivePatient();
             */
            addFakeData();
            outputStream.writeUTF(printAllDatabase());
            int choice=-1;
            while (choice != 6) {
                 choice = inputStream.readInt();
                switch (choice) {
                    case 1:
                        receiveDoctor();
                        break;
                    case 2:
                        receivePatient();
                        break;
                    
                    case 3:
                        receiveAppointments();
                        break;
                    case 4:
                        DeleteAppointment();
                        break;
                    case 5:
                        outputStream.writeUTF(printAllDatabase());
                        break;
                    default:
                        break;

                }
            }
            //sending all info and deleted appointments info after the program finishes
            outputStream.writeUTF(printAllDatabase());
            outputStream.writeUTF(callDeletedPatients());
            outputStream.writeUTF("all patients info was saved in a text file");
            System.out.println("all patients info was saved in a text file");
            file();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
    public static void file(){
        try {
            File His = new File("His.txt");

            if(!His.exists()) {
                His.createNewFile();
            }
            PrintWriter pw = new PrintWriter(His);
            for (Patient P : patients) {
                pw.println("The Patient Name is : " + P.getName());
                pw.println("The Patient ID is : " + P.getID());
                pw.println("The Patient Phone number Is : " + P.getPhoneNumber());
                pw.println("The Patient E-Mail Is : " + P.getEmail());
            }
            pw.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String callDeletedPatients() {
        String x="";
        for(int id : deletedPatientsIds){
            for(Patient patient:patients){
                if(patient.getID()==id){
                    x+="calling : "+patient.getName()+" to tell him that his appointment was canceled\n";
                }
            }
        }

        System.out.println(x);
        return x;
    }


    public static void receiveDoctor() throws IOException {
        int doctorId = inputStream.readInt();
        String docName = inputStream.readUTF();
        String docDays = inputStream.readUTF();
        String docStartTime = inputStream.readUTF();
        String docEndTime = inputStream.readUTF();
        String[] start = docStartTime.split(":");
        String[] end = docEndTime.split(":");
        Week week = new WeekBuilder(LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1])), LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]))).setSun(docDays.contains("sun")).setMon(docDays.contains("mon")).setTue(docDays.contains("tue")).setWed(docDays.contains("wed")).setThu(docDays.contains("thu")).setFri(docDays.contains("fri")).setSat(docDays.contains("sat")).build();
        doctors.add(new Doctor(docName, week,doctorId));
    }

    public static void receivePatient() throws IOException {
        patients.add(new Patient(inputStream.readUTF(), inputStream.readInt(), inputStream.readInt(), inputStream.readUTF()));
    }

    public static void receiveAppointments() throws IOException {
        int doctorId = inputStream.readInt();
        int patientId = inputStream.readInt();
        String startTime = inputStream.readUTF();
        String endTime = inputStream.readUTF();
        String appointmentDay = inputStream.readUTF();
        LocalTime start = LocalTime.of(Integer.parseInt(startTime.split(":")[0]), Integer.parseInt(startTime.split(":")[1]));
        LocalTime end = LocalTime.of(Integer.parseInt(endTime.split(":")[0]), Integer.parseInt(endTime.split(":")[1]));
        DayOfWeek day;
        switch (appointmentDay) {
            case "sun":
                day = DayOfWeek.SUNDAY;
                break;
            case "mon":
                day = DayOfWeek.MONDAY;
                break;
            case "tue":
                day = DayOfWeek.TUESDAY;
                break;
            case "wed":
                day = DayOfWeek.WEDNESDAY;
                break;
            case "thu":
                day = DayOfWeek.THURSDAY;
                break;
            case "fri":
                day = DayOfWeek.FRIDAY;
                break;
            default:
                day = DayOfWeek.SATURDAY;
                break;
        }
        Appointment appointment = new Appointment(patientId, start, end, day);
        for (Doctor doc:doctors){
            if(doc.getId()==doctorId){
                if(doc.validateAndAdd(appointment)==2){
                    outputStream.writeInt(2);
                    doc.addAppointment(appointment);
                }
                else if(doc.validateAndAdd(appointment)==1)
                    outputStream.writeInt(1);
                else if(doc.validateAndAdd(appointment)==0)
                    outputStream.writeInt(0);
                else if(doc.validateAndAdd(appointment)==3)
                    outputStream.writeInt(3);
            }
        }


    }

    
    public static void DeleteAppointment() throws IOException
    {
        List<DayOfWeek> list = new ArrayList<DayOfWeek>();
        int DocID = inputStream.readInt();
        String Day = inputStream.readUTF();
        if(Day=="0"){
            list.add(DayOfWeek.SATURDAY);
            list.add(DayOfWeek.SUNDAY);
            list.add(DayOfWeek.MONDAY);
            list.add(DayOfWeek.TUESDAY);
            list.add(DayOfWeek.THURSDAY);
            list.add(DayOfWeek.WEDNESDAY);
            list.add(DayOfWeek.FRIDAY);
        }
        else{
            String []arr = Day.split(" ");
            String Sun = "Sun";
            for (int i = 0; i < arr.length; i++) {
                switch(arr[i].toUpperCase()){
                    case "SUN":
                        list.add(DayOfWeek.SUNDAY);
                        break;
                    case "MON":
                        list.add(DayOfWeek.MONDAY);
                        break;
                    case "TUE":
                        list.add(DayOfWeek.TUESDAY);
                        break;
                    case "WED":
                        list.add(DayOfWeek.WEDNESDAY);
                        break;
                    case "THU":
                        list.add(DayOfWeek.THURSDAY);
                        break;
                    case "FRI":
                        list.add(DayOfWeek.FRIDAY);
                        break;
                    case "SAT":
                        list.add(DayOfWeek.SATURDAY);
                        break;
                }
                //list.set(i, DayOfWeek.valueOf(arr[i]));
                //list.add(DayOfWeek.valueOf(arr[i]));
            }
        }

        for (Doctor Doc : doctors) {
            if(DocID==0){
                Doc.DeleteDay(list);
            }
            else if(Doc.getId() == DocID)
                Doc.DeleteDay(list);
        }
    }
    public static String printAllDatabase() {
        String x="";
        for(Doctor doc : doctors){
            x+=doc.toString();
            System.out.println(doc);
        }
        for(Patient pat : patients){
            x+=pat.toString();
            System.out.println(pat);
        }
       return x;

    }
}
