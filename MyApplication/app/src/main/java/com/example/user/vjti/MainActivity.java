package com.example.user.vjti;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    final DatabaseReference sellersfirebase = FirebaseDatabase.getInstance().getReference("LiveTracking");
    TextView textView;
    MyUserModel myuser = new MyUserModel("","null",0,0);
    String trainkey = "My train";//sellersfirebase.push().getKey();
    String trainkey2 = "My other train";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String userkey = sellersfirebase.push().getKey();
        myuser.setId(userkey);
        sellersfirebase.child("Users").child(userkey).setValue(myuser);
        myuser.updateLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =(TextView) findViewById(R.id.location);


        userkey = sellersfirebase.child(trainkey).push().getKey();
        UserModel userModel = new UserModel(userkey,trainkey,0.02,0.02);
        sellersfirebase.child("Users").child(userkey).setValue(userModel);
        userModel.updateLocation();

        userkey = sellersfirebase.child(trainkey2).push().getKey();
        userModel = new UserModel(userkey,trainkey2,0.02,0.02);
        sellersfirebase.child("Users").child(userkey).setValue(userModel);
        userModel.updateLocation();

}



    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    protected void onStart() {
        super.onStart();
        sellersfirebase.child("Trains").child(trainkey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                double d = distance(myuser.getLatitude(), myuser.getLongitude(),dataSnapshot.getValue(UserModel.class).getLatitude(),dataSnapshot.getValue(UserModel.class).getLongitude());
                if(d>0.21 && !myuser.getCurrentTrain().matches(trainkey))
                    textView.setText(Double.toString(d));
                else if(d<0.21 && !myuser.getCurrentTrain().matches(trainkey))
                {
                    myuser.setCurrentTrain(trainkey);
                    textView.setText("Your are in the Train's Proximity!");
                }
                else if(d>0.21 && myuser.getCurrentTrain().equals("My train"))
                {
                    myuser.setCurrentTrain("null");
                    textView.setText(Double.toString(d));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                double d = distance(myuser.getLatitude(), myuser.getLongitude(),dataSnapshot.getValue(UserModel.class).getLatitude(),dataSnapshot.getValue(UserModel.class).getLongitude());
                if(d>0.21 && myuser.getCurrentTrain().matches("null"))
                    textView.setText(Double.toString(d));
                else if(d<0.21 && myuser.getCurrentTrain().matches("null"))
                {
                    myuser.setCurrentTrain("My train");
                    textView.setText("Your are in the Train's Proximity!");
                }
                else if(d>0.21 && !myuser.getCurrentTrain().equals("null"))
                {
                    sellersfirebase.child("Trains").child(myuser.getCurrentTrain()).child(myuser.getId()).removeValue();
                    myuser.setCurrentTrain("null");
                    textView.setText(Double.toString(d));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

