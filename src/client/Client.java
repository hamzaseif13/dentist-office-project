package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static Socket socket;
    public static Scanner scanner;
    public static DataInputStream inputStream;
    public static DataOutputStream outputStream;
    static public String yesOrNo = "";

    public static void addDoctors() {
        try {

            System.out.println("Enter a doctor name");
            String doctorName = scanner.nextLine();

            System.out.println("Enter a doctor week days. Ex. sun mon thu :");
            String doctorDays = scanner.nextLine();

            System.out.println("Enter a doctor start time :Ex 11:30");
            String doctorStartTime = scanner.nextLine();

            System.out.println("Enter a doctor end time :Ex 17:50");
            String doctorEndTime = scanner.nextLine();

            //sending name
            outputStream.writeUTF(doctorName);
            //sending days
            outputStream.writeUTF(doctorDays);
            //sending start time
            outputStream.writeUTF(doctorStartTime);
            //sending end time
            outputStream.writeUTF(doctorEndTime);

        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public static void addPatients() {
        try {
            System.out.println("Enter patient's name : ");
            String patientName = scanner.nextLine();

            System.out.println("Enter patient's ID : ");
            int patientID = scanner.nextInt();

            System.out.println("Enter patient's phone number : ");
            int patientPhoneNumber = scanner.nextInt();

            System.out.println("Enter patient's email : ");
            String patientEmail = scanner.next();
            //Name
            outputStream.writeUTF(patientName);
            //ID
            outputStream.writeInt(patientID);
            //Number
            outputStream.writeInt(patientPhoneNumber);
            //Email
            outputStream.writeUTF(patientEmail);
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public static void addAppointments() {
        try {
            System.out.println("Enter patient's id :");
            int patientId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter patient's start time appointment : ex. 11:30");
            String startTimeAppointment = scanner.nextLine();

            System.out.println("Enter patient's end time appointment : ex. 11:30");
            String endTimeAppointment = scanner.nextLine();

            System.out.println("enter patient's day appointment : ");
            String appointmentDay = scanner.nextLine();
            //IDApp
            outputStream.writeInt(patientId);
            //StartTimeApp
            outputStream.writeUTF(startTimeAppointment);
            //EndTimeApp
            outputStream.writeUTF(endTimeAppointment);
            //sending day
            outputStream.writeUTF(appointmentDay);
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 5000);
            scanner = new Scanner(System.in);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            yesOrNo = "";
            addDoctors();
            addPatients();
            int choice = -1;

            while (choice != 4) {
                System.out.println("what do you wanna do next ?");
                System.out.println("1- add more doctors \n2- add more patients\n3- add appointments\n4- leave the system");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        outputStream.writeInt(1);
                        addDoctors();
                        break;
                    case 2:
                        outputStream.writeInt(2);
                        addPatients();
                        break;
                    case 3:
                        outputStream.writeInt(3);
                        addAppointments();
                        break;
                    default:
                        outputStream.writeInt(4);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
