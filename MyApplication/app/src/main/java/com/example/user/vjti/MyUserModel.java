package com.example.user.vjti;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class MyUserModel implements Parcelable {
    public String id;
    public  double latitude, longitude;
    public String currentTrain;

    public MyUserModel(String id, String currentTrain, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentTrain = currentTrain;
    }

    protected MyUserModel(Parcel in) {
        id = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        currentTrain = in.readString();
    }

    public static final Creator<MyUserModel> CREATOR = new Creator<MyUserModel>() {
        @Override
        public MyUserModel createFromParcel(Parcel in) {
            return new MyUserModel(in);
        }

        @Override
        public MyUserModel[] newArray(int size) {
            return new MyUserModel[size];
        }
    };

    public String getCurrentTrain() {
        return currentTrain;
    }

    public void setCurrentTrain(String currentTrain) {
        this.currentTrain = currentTrain;
    }

    public MyUserModel()
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
                timerHandler.postDelayed(updater, 1000);
                double val = 0;
                if(!currentTrain.matches("null")) {
                    trainsfirebase.child(currentTrain).child(id).child("latitude").setValue(latitude);
                    trainsfirebase.child(currentTrain).child(id).child("longitude").setValue(latitude);
                }
            }
        };
        timerHandler.post(updater);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(currentTrain);
    }
}
