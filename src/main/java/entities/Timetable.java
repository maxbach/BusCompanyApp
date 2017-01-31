package entities;

import javax.persistence.*;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by maxbacinskiy on 27.01.17.
 */
@Entity
@Table(name = "timetables")
public class Timetable {
    @Id @GeneratedValue
    @Column(name = "timetable_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "timetable_bus")
    private Bus bus;

    @Column(name = "time_bus")
    private Time time;

    public Timetable(Bus bus, Time time) {
        this.bus = bus;
        this.time = time;
    }

    public Timetable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int timetableID) {
        this.id = timetableID;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getBeautifulTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(time);
    }
}
