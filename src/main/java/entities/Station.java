package entities;

import javax.persistence.*;

/**
 * Created by maxbacinskiy on 25.01.17.
 */

@Entity
@Table(name = "stations")
public class Station {


    @Id @GeneratedValue
    @Column(name = "station_id")
    private int stationID;

    @Column(name = "station_name", unique = true, nullable = false)
    private String name;


    public Station(String name) {
        this.name = name;
    }

    public Station() {
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
