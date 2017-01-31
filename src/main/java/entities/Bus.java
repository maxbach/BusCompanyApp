package entities;

import javax.persistence.*;

/**
 * Created by maxbacinskiy on 25.01.17.
 */

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @Column(name = "bus_id")
    private int id;

    @Column(name = "bus_seats")
    private int seats;

    @ManyToOne
    @JoinColumn(name = "bus_station_a")
    private Station stationA;

    @ManyToOne
    @JoinColumn(name = "bus_station_b")
    private Station stationB;

    public Bus() {
    }

    public Bus(int id, int seats, Station stationA, Station stationB) {
        this.id = id;
        this.seats = seats;
        this.stationA = stationA;
        this.stationB = stationB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Station getStationA() {
        return stationA;
    }

    public void setStationA(Station stationA) {
        this.stationA = stationA;
    }

    public Station getStationB() {
        return stationB;
    }

    public void setStationB(Station stationB) {
        this.stationB = stationB;
    }
}
