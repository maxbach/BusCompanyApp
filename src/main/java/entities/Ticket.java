package entities;

import javax.persistence.*;

/**
 * Created by maxbacinskiy on 28.01.17.
 */
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id @GeneratedValue
    @Column(name = "ticket_id")
    private int ticketID;

    @ManyToOne
    @JoinColumn(name = "ticket_timatable")
    private Timetable timetable;

    @ManyToOne
    @JoinColumn(name = "ticket_buyer")
    private User user;

    public Ticket(Timetable timetable, User user) {
        this.timetable = timetable;
        this.user = user;
    }

    public Ticket() {
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
