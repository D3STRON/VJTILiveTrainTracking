package com.example.user.vjti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class InAppTicketing extends AppCompatActivity implements onItemClick{
    Button mSourceBtn, mDestinationBtn, mBookBtn;
    public TextView mSource,mDestination,fare,fareVal;
    String Source,Destination;
    private RecyclerView mRecyclerView;
    public ArrayList<Station> mDataSet = new ArrayList<Station>();
    private StationAdapter mAdapter;
    Activity activity;
    onItemClick itemClick;
    Context context;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_ticketing);
        getSupportActionBar().setElevation(0);
        mSource = (TextView) findViewById(R.id.source);
        mDestination = (TextView) findViewById(R.id.destination);
        mSourceBtn = (Button) findViewById(R.id.sourcebtn);
        mDestinationBtn = (Button) findViewById(R.id.destinationBtn);
        mBookBtn = (Button) findViewById(R.id.book);
        fare = (TextView) findViewById(R.id.fare);
        fareVal = (TextView) findViewById(R.id.fareVal);
        Intent intent = getIntent();
        activity = this;
        itemClick = this;
        Source = intent.getStringExtra("source");
        Destination = intent.getStringExtra("destination");
        mSource.setText(Source);
        mDestination.setText(Destination);
        mRecyclerView = (RecyclerView) findViewById(R.id.stationList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter = new StationAdapter(mDataSet,this,InAppTicketing.this,this,flag);
        mSourceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                mAdapter = new StationAdapter(mDataSet,activity,InAppTicketing.this,itemClick,flag);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        mDestinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                mAdapter = new StationAdapter(mDataSet,activity,InAppTicketing.this,itemClick,flag);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        mBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity( new Intent(InAppTicketing.this,PaymentGateway.class));
            }
        });
        String station[] = {"Mumbai CST","Dadar","Kurla","Thane"};
        for(int i = 0;i<station.length;i++){
            mDataSet.add(
                    new Station(station[i])
            );
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSource(String value,String flag) {
        if(flag.equals("source")){
        Source = value;
        mSource.setText(Source);
        }
        else {
            Destination = value;
            mDestination.setText(Destination);
            fare.setVisibility(View.VISIBLE);
            fareVal.setVisibility(View.VISIBLE);
        }
    }
}
