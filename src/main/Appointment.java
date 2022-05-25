package main;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int patientID;
    private LocalTime endTime;
    private LocalTime startTime;
    private LocalDate date;

    public Appointment(int patientID, LocalTime endTime, LocalTime startTime, LocalDate date) {
        this.patientID = patientID;
        this.endTime = endTime;
        this.startTime = startTime;
        this.date = date;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
