package main;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Doctor {
    private String name;
    private Week workingSchedule;
    private final List<Appointment> appointments;
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private int Id;
    public Doctor(String name, Week workingSchedule,int Id) {
        this.name = name;
        this.workingSchedule = workingSchedule;
        this.appointments = new ArrayList<>() ;
        this.Id=Id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Week getWorkingSchedule() {
        return workingSchedule;
    }
    public void setWorkingSchedule(Week workingSchedule) {
        this.workingSchedule = workingSchedule;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void addAppointment(Appointment appointment) {
        this.appointments.add( appointment);
    }
    public void cancelAppointment(){
        appointments.clear();
    }

    public String getAppointmentsString(){
        String y="\nAppointments : \n";
        for(Appointment appointment : appointments){
            y+=appointment.toString()+"\n";
        }
        return y;
    }
    public void DeleteDay(List<DayOfWeek> list)
    {
        for (DayOfWeek Day : list) {
        for (Appointment App : appointments) {
            if(Day == App.getDay())
            {
                try {
                    App.DeleteApp();
                } catch (Throwable ex) {
                    Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }
    }
    private boolean checkIntersection(Appointment first,Appointment second){
        if(first.getDay()!=second.getDay()){
            return false;
        }
        if(first.getStartTime()==second.getStartTime())return true;
        if(first.getStartTime().isBefore(second.getStartTime())){
            if(first.getEndTime().isAfter(second.getStartTime()))return true;
            else return false;
        }
        else {
            if(second.getEndTime().isAfter(first.getStartTime()))return true;
            else return false;
        }

    }
    public int validateAndAdd(Appointment appointmentcheck) {
        DayOfWeek day;
        if(appointmentcheck.getDay()==DayOfWeek.SUNDAY&&!workingSchedule.isSun()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.MONDAY&&!workingSchedule.isMon()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.TUESDAY&&!workingSchedule.isTue()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.WEDNESDAY&&!workingSchedule.isWed()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.THURSDAY&&!workingSchedule.isThu()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.FRIDAY&&!workingSchedule.isFri()){
            return 0;
        }
        else if(appointmentcheck.getDay()==DayOfWeek.SATURDAY&&!workingSchedule.isSat()){
            return 0;
        }
        if(appointmentcheck.getStartTime().isBefore(workingSchedule.getStartTime())
                ||appointmentcheck.getEndTime().isAfter(workingSchedule.getEndTime())){
            return 3;
        }
        for(Appointment appointment:appointments){
            if(checkIntersection(appointment,appointmentcheck))return 1;
        }
        return 2;
    }
    @Override
    public String toString() {
        return "\n------------------------------\n"+
                this.name+"\nstart time "+workingSchedule.getStartTime()+" | end time "+
                workingSchedule.getEndTime()+" \n"+workingSchedule+getAppointmentsString()+"\n"+
                "------------------------------";

    }
}