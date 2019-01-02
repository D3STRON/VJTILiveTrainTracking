package com.example.user.vjti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DelayList extends AppCompatActivity {

    private ArrayList<StationModel> mDataSet = new ArrayList<StationModel>();
    private DelayAdapater mAdapter;
    final DatabaseReference sellersfirebase = FirebaseDatabase.getInstance().getReference("LiveTracking");
    MyUserModel myuser = new MyUserModel("My User","null",0.10,0.10);
    private RecyclerView mRecyclerView;
    String trainkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_list);
        String station[] = {"Mumbai CST","Byculla","Dadar","Kurla","Ghatkoper","Mulund","Thane"};
        double latlan = 0.05;
        for(int i = 0;i<station.length;i++){
            mDataSet.add(
                    new StationModel(station[i],"1",latlan,latlan,0.0,"00:00")
            );
            latlan += 0.05;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.delayRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter = new DelayAdapater(mDataSet,this,DelayList.this);
        mRecyclerView.setAdapter(mAdapter);
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
        SharedPreferences mPreferences= getSharedPreferences("check", MODE_PRIVATE);
        final String userkey = sellersfirebase.push().getKey();
        myuser.setId(userkey);
        sellersfirebase.child("Users").child("My USER").setValue(myuser);
        myuser.updateLocation();
        Intent intent = getIntent();
        trainkey = intent.getStringExtra("uid");
        if(!mPreferences.getBoolean(trainkey,false))
        {
            SharedPreferences.Editor editor = getSharedPreferences("check", MODE_PRIVATE).edit();
            editor.putBoolean(trainkey,true);
            editor.apply();
            Initialize();
        }
        Listen();
    }


    public void Initialize()
    {
        // contributons
        final String userkey2 = sellersfirebase.child(trainkey).push().getKey();
        UserModel userModel = new UserModel(userkey2,trainkey,0.02,0.02);
        sellersfirebase.child("Users").child(userkey2).setValue(userModel);
        userModel.updateLocation();

        final String userkey3 = sellersfirebase.child(trainkey).push().getKey();
        userModel = new UserModel(userkey3,trainkey,0.02,0.02);
        sellersfirebase.child("Users").child(userkey3).setValue(userModel);
        userModel.updateLocation();
        ///////////
    }


    public void Listen()
    {
        sellersfirebase.child("Trains").child(trainkey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateStation(dataSnapshot);
                update(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateStation(dataSnapshot);
                update(dataSnapshot);
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


    public void update(DataSnapshot dataSnapshot)
    {
        double d = distance(myuser.getLatitude(), myuser.getLongitude(),dataSnapshot.getValue(UserModel.class).getLatitude(),dataSnapshot.getValue(UserModel.class).getLongitude());
        if(d<0.5 && myuser.getCurrentTrain().matches("null"))
        {
            myuser.setCurrentTrain(trainkey);
        }
        else if(d>0.5 && !myuser.getCurrentTrain().equals("null"))
        {
            sellersfirebase.child("Trains").child(myuser.getCurrentTrain()).child(myuser.getId()).removeValue();
            myuser.setCurrentTrain("null");
        }
    }

    public void updateStation(DataSnapshot dataSnapshot)
    {
        for(int i =0 ;i<mDataSet.size(); i++)
        {
            double c = 0.5;
            double d = distance(mDataSet.get(i).getLat(), mDataSet.get(i).getLon(),dataSnapshot.getValue(UserModel.class).getLatitude(),dataSnapshot.getValue(UserModel.class).getLongitude());
            mDataSet.get(i).setDistance(d);
            if(mDataSet.get(i).getDistance()<c && mDataSet.get(i).getStatus().matches("1")) {
                mDataSet.get(i).setStatus("2");
                mDataSet.get(i).setTimeTOReach(Double.toString(d-0.2).substring(0,3));
            }
            else if(mDataSet.get(i).getDistance()>c && mDataSet.get(i).getStatus().matches("2"))
            {
                mDataSet.get(i).setStatus("3");
            }
            else
            {
                mDataSet.get(i).setTimeTOReach(Double.toString(d-0.2).substring(0,3));
            }

            if(mDataSet.get(i).getCurrentDist() == -1)
            {
                mDataSet.get(i).setCurrentDist(d);
                mDataSet.get(i).setStatus("1");
            }
            else if(d>mDataSet.get(i).getCurrentDist())
            {
                mDataSet.get(i).setStatus("3");
                mDataSet.get(i).setCurrentDist(d);

            }
            else if(d<mDataSet.get(i).getCurrentDist())
            {
                mDataSet.get(i).setStatus("1");
                mDataSet.get(i).setCurrentDist(d);
            }
        }

       mAdapter.notifyDataSetChanged();
    }
}

