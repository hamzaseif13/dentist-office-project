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
            do {
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
                System.out.println("Do you want to add another Doctor ? Y/N");
                yesOrNo = scanner.next();
                //sending yes or no
                outputStream.writeUTF(yesOrNo);
            }
            while (yesOrNo.equalsIgnoreCase("Y"));
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public static void addPatients() {
        try {
            do {
                System.out.println("Enter patient's name : ");
                scanner.nextLine();
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

                System.out.println("Do you want to add another Patient ? Y/N");
                yesOrNo = scanner.next();
                //sending yes or no
                outputStream.writeUTF(yesOrNo);


            } while (yesOrNo.equalsIgnoreCase("Y"));
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public static void addAppointments() {
        try {
            System.out.println("Enter patient's id :");
            int patientId = scanner.nextInt();

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

            while (choice != 0) {
                System.out.println("what do you wanna do next ?");
                System.out.println("1- add more doctors \n2- add more patients\n3- add appointments\n4- leave the system");
                choice = scanner.nextInt();
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
                    default:outputStream.writeInt(0);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
