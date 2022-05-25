package main;

import java.time.LocalTime;

public class Week {
    private final boolean sun;
    private final boolean mon;
    private final boolean tue;
    private final boolean wed;
    private final boolean thu;
    private final boolean fri;
    private final boolean sat;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Week(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, LocalTime startTime, LocalTime endTime) {
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
       String x ="";
       if(sun)x+="Sun\n";
        if(mon)x+="Mon\n";
        if(tue)x+="Tue\n";
        if(wed)x+="Wed\n";
        if(thu)x+="Thu\n";
        if(fri)x+="Fri\n";
        if(sat)x+="Sat\n";
        return x;
    }
}
