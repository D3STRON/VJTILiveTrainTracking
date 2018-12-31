package com.example.user.vjti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 12/29/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private ArrayList<Train> trainSet;
    private Activity activity;
    private Context mContext;

    public CustomAdapter(ArrayList<Train> mDataSet,  Activity activity, Context mContext) {
        this.trainSet = mDataSet;
        this.activity = activity;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        final Train train = trainSet.get(position);
        viewHolder.Time.setText(train.getTraintime());
        viewHolder.Source.setText(train.getSource());
        viewHolder.Destination.setText(train.getDestination());
        viewHolder.Destinationbig.setText(train.getDestination());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = trainSet.get(position).getUid();
                Intent i = new Intent(mContext,DelayList.class);
                i.putExtra("uid",uid);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainSet.size();
    }
    public void refresh(ArrayList<Train> mDataSet) {
        this.trainSet = mDataSet;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Time;
        public TextView Source;
        public TextView Destination;
        public TextView Destinationbig;
        public MyViewHolder(View itemView) {
            super(itemView);
            Time = (TextView) itemView.findViewById(R.id.time);
            Source = (TextView) itemView.findViewById(R.id.source);
            Destination = (TextView) itemView.findViewById(R.id.destination);
            Destinationbig = itemView.findViewById(R.id.DestinationBig);
        }
    }
}
