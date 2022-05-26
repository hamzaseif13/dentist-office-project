package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",5000);
            Scanner scanner = new Scanner(System.in);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            String yesOrNo="";
            do{
                System.out.println("Enter a doctor name");
                String doctorName= scanner.nextLine();

                System.out.println("Enter a doctor week days. Ex. sun mon thu :");
                String doctorDays= scanner.nextLine();

                System.out.println("Enter a doctor start time :Ex 11:30");
                String doctorStartTime= scanner.nextLine();

                System.out.println("Enter a doctor end time :Ex 17:50");
                String doctorEndTime= scanner.nextLine();

                //sending name
                outputStream.writeUTF(doctorName);
                //sending days
                outputStream.writeUTF(doctorDays);
                //sending start time
                outputStream.writeUTF(doctorStartTime);
                //sending end time
                outputStream.writeUTF(doctorEndTime);
                System.out.println("Do you want to add another Doctor ? Y/N");
                yesOrNo=scanner.next();
                //sending yes or no
                outputStream.writeUTF(yesOrNo);
            }
            while (yesOrNo.equalsIgnoreCase("Y"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
