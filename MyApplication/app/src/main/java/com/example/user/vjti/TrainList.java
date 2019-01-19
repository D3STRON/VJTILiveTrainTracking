package com.example.user.vjti;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class TrainList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Train>  mDataSet = new ArrayList<Train>();
    private CustomAdapter mAdapter;
    String uid[] = {"1", "2", "3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        getSupportActionBar().setElevation(0);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter = new CustomAdapter(mDataSet,this,TrainList.this);
        String source[] = {"Mumbai CST","Dadar","Kurla"};
        String destination[] = {"Thane","Kalyan","Titwala"};
        String time[] = {"9:40 AM","9:55 AM","10:10 AM"};

        for(int i = 0 ; i < source.length; i++){
            Train t = new Train(time[i],source[i],destination[i],uid[i]);
            mDataSet.add(t);
        }
        mAdapter.refresh(mDataSet);
        mRecyclerView.setAdapter(mAdapter);
        Toast.makeText(this,mDataSet.size()+ "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        for(int i = 0 ; i<uid.length; i++)
        {
            SharedPreferences mPreferences= getSharedPreferences("check", MODE_PRIVATE);
            SharedPreferences.Editor editor = getSharedPreferences("check", MODE_PRIVATE).edit();
            if(!mPreferences.getBoolean(uid[i],false))
            {
                editor.putBoolean(uid[i],false);
                editor.apply();
            }
        }
    }


}

