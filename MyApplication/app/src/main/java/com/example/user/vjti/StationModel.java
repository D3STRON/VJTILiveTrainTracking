package com.example.user.vjti;

/**
 * Created by hp on 12/29/2018.
 */

public class StationModel {
    String station,status = "1";
    double lat, lon;
    double distance;
    String timeTOReach;
    double currentDist = -1;

    public double getCurrentDist() {
        return currentDist;
    }

    public void setCurrentDist(double currentDist) {
        this.currentDist = currentDist;
    }

    public StationModel(String station, String status, double lat, double lon, double distance, String timeTOReach) {
        this.station = station;
        this.status = status;
        this.lat = lat;
        this.lon = lon;
        this.distance = distance;
        this.timeTOReach = timeTOReach;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTimeTOReach() {
        return timeTOReach;
    }

    public void setTimeTOReach(String timeTOReach) {
        this.timeTOReach = timeTOReach;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
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
