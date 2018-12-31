package com.example.user.vjti;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 12/29/2018.
 */

public class DelayAdapater extends RecyclerView.Adapter<DelayAdapater.MyViewHolder> {
    private ArrayList<StationModel> trainSet;
    private Activity activity;
    private Context mContext;

    public DelayAdapater(ArrayList<StationModel> trainSet, Activity activity, Context mContext) {
        this.trainSet = trainSet;
        this.activity = activity;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delay_row, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final StationModel delay = trainSet.get(position);
        holder.Station.setText(delay.getStation());
        holder.TimeVal.setText(delay.getTimeTOReach()+" sec");
        //Picasso.with(mContext).load(delay.getStatus()).into(holder.Status);
        if(trainSet.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.Status.setImageResource(R.drawable.green_circle);
        }
        else if(trainSet.get(position).getStatus().equalsIgnoreCase("2")){
            holder.Status.setImageResource(R.drawable.yellow_circle);
        }
        else if(trainSet.get(position).getStatus().equalsIgnoreCase("3")){
            holder.Status.setImageResource(R.drawable.red_circle);
            holder.TimeVal.setText("Left");
        }
        holder.DistanceVal.setText( Double.toString(delay.getDistance()).substring(0,3)+" kms");
    }

    @Override
    public int getItemCount() {
        return trainSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Station;
        public ImageView Status;
        public TextView DistanceVal,TimeVal;
        public MyViewHolder(View itemView) {
            super(itemView);
            Station = (TextView) itemView.findViewById(R.id.stationName);
            Status = (ImageView) itemView.findViewById(R.id.status);
            DistanceVal = itemView.findViewById(R.id.distanceVal);
            TimeVal = itemView.findViewById(R.id.timeVal);
        }
    }
}
