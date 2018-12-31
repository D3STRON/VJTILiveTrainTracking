package com.example.user.vjti;

/**
 * Created by hp on 12/29/2018.
 */

public class Station {
    String station,status;
    float lat, lon;

    public Station(String station, String status) {
        this.station = station;
        this.status = status;
    }

    public Station(String station) {
        this.station = station;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
