package main;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String []args){
        Patient patient = new Patient("hamza",1,077,0,0,"",null);
        Week ramziDocWeek=new  WeekBuilder(LocalTime.of(8,30),LocalTime.of(17,0))
                .setSun(true).setTue(true).setThu(true).build();
        Doctor ramzi= new Doctor("ramzi",ramziDocWeek);
        ramzi.addAppointment(new Appointment(1,LocalTime.of(11,0),
                LocalTime.of(12,0), LocalDate.of(2022,5,26)));
        System.out.println(ramzi);
        //hi
    }
}
