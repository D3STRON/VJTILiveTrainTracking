package com.example.user.vjti;

import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {
    public String id;
    public  double latitude, longitude;
    public String currentTrain;

    public UserModel(String id, String currentTrain, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentTrain = currentTrain;
    }

    public String getCurrentTrain() {
        return currentTrain;
    }

    public void setCurrentTrain(String currentTrain) {
        this.currentTrain = currentTrain;
    }

    public UserModel()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    Runnable updater;
    void updateLocation() {
        final DatabaseReference trainsfirebase = FirebaseDatabase.getInstance().getReference("LiveTracking").child("Trains");
        final Handler timerHandler = new Handler();
        updater = new Runnable() {
            @Override
            public void run() {
                timerHandler.postDelayed(updater, 100);
                double r = Math.random();
                double val = 0.0005;
                latitude = (latitude +  val);
                longitude = (longitude + val);
                trainsfirebase.child(currentTrain).child(id).child("latitude").setValue(latitude);
                trainsfirebase.child(currentTrain).child(id).child("longitude").setValue(latitude);
                if(latitude>=0.4)
                {
                    latitude = 0.02;
                    longitude = 0.02;
                }
            }
        };
        timerHandler.post(updater);
    }
}
