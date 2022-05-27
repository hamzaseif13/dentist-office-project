package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    static DataInputStream inputStream;
    static String yesOrNo;
    static List<Doctor> doctors = new ArrayList<Doctor>();
    static List<Patient> patients = new ArrayList<Patient>();
    static List<Appointment> appointments = new ArrayList<Appointment>();

    public static void main(String[] args) {

        try {
            server = new ServerSocket(5000);
            receptions = server.accept();
            outputStream = new DataOutputStream(receptions.getOutputStream());
            inputStream = new DataInputStream(receptions.getInputStream());
            yesOrNo = "";
            receiveDoctor();
            receivePatient();
            int choice=-1;
            while (choice != 4) {
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


                }
            }
            printAllDatabase();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }


    public static void receiveDoctor() throws IOException {
        String docName = inputStream.readUTF();
        String docDays = inputStream.readUTF();
        String docStartTime = inputStream.readUTF();
        String docEndTime = inputStream.readUTF();
        String[] start = docStartTime.split(":");
        String[] end = docEndTime.split(":");
        Week week = new WeekBuilder(LocalTime.of(Integer.parseInt(start[0]), Integer.parseInt(start[1])), LocalTime.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]))).setSun(docDays.contains("sun")).setMon(docDays.contains("mon")).setTue(docDays.contains("tue")).setWed(docDays.contains("wed")).setThu(docDays.contains("thu")).setFri(docDays.contains("fri")).setSat(docDays.contains("sat")).build();

        doctors.add(new Doctor(docName, week));

    }

    public static void receivePatient() throws IOException {
        patients.add(new Patient(inputStream.readUTF(), inputStream.readInt(), inputStream.readInt(), inputStream.readUTF()));
    }

    public static void receiveAppointments() throws IOException {
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
        appointments.add(new Appointment(patientId, start, end, day));
    }

    public static void printAllDatabase() {
        doctors.forEach(doctor -> System.out.println(doctor));
        patients.forEach(patient -> System.out.println(patient));
        appointments.forEach(appointment -> System.out.println(appointment));
    }
}
