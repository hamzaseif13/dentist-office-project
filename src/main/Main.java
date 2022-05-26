package main;

import javax.print.Doc;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ServerSocket server;
    static Socket receptions;
    static DataOutputStream outputStream;
    static DataInputStream inputStream;
    static String yesOrNo;
    static List<Doctor> doctors = new ArrayList<Doctor>();
    static List<Patient> patients = new ArrayList<Patient>();
    public static void main(String[] args) {

        try {
            server = new ServerSocket(5000);
            receptions = server.accept();
            outputStream = new DataOutputStream(receptions.getOutputStream());
             inputStream = new DataInputStream(receptions.getInputStream());
             yesOrNo = "";
            addDoctors();
            addPatients();
            int choice= inputStream.readInt();
            while (choice != 0) {
                switch (choice) {
                    case 1:
                        addDoctors();outputStream.writeInt(1);
                        break;
                    case 2:
                        addPatients();outputStream.writeInt(2);
                        break;
                    case 3:
                        addAppointments();outputStream.writeInt(3);
                        break;
                }
            }






        } catch (Exception exception) {
            System.out.println(exception);
        }
    }


    public static void addDoctors() throws IOException {
        do {
            String docName = inputStream.readUTF();
            String docDays = inputStream.readUTF();
            String docStartTime = inputStream.readUTF();
            String docEndTime = inputStream.readUTF();

            String[] start = docStartTime.split(":");
            String[] end = docEndTime.split(":");
            Week week = new WeekBuilder(LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]))
                    , LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1])))
                    .setSun(docDays.contains("sun")).setMon(docDays.contains("mon"))
                    .setTue(docDays.contains("tue")).setWed(docDays.contains("wed"))
                    .setThu(docDays.contains("thu")).setFri(docDays.contains("fri"))
                    .setSat(docDays.contains("sat"))
                    .build();

            doctors.add(new Doctor(docName, week));
            yesOrNo = inputStream.readUTF();
        } while (yesOrNo.equalsIgnoreCase("Y"));
    }
    public static void addPatients()throws  IOException{
        do {
            patients.add(new Patient(inputStream.readUTF(), inputStream.readInt(), inputStream.readInt(), inputStream.readUTF()));
            yesOrNo = inputStream.readUTF();

        } while (yesOrNo.equalsIgnoreCase("Y"));
    }
    public static void addAppointments(){}
}
