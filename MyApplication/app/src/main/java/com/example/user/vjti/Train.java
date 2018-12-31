package com.example.user.vjti;


import java.sql.Time;

/**
 * Created by hp on 12/29/2018.
 */

public class Train {
    private String traintime;
    private String source;
    private String destination;
    private String uid;

    public Train(String traintime, String source, String destination, String uid) {
        this.traintime = traintime;
        this.source = source;
        this.destination = destination;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTraintime() {
        return traintime;
    }

    public void setTraintime(String traintime) {
        this.traintime = traintime;
    }
}
