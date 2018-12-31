package com.example.user.vjti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 12/29/2018.
 */

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.MyViewHolder> {
    private ArrayList<Station> stationset;
    private Activity activity;
    private Context mContext;
    public String Source,Destination;
    private onItemClick mCallback;
    private int flag;

    public StationAdapter(ArrayList<Station> stationset, Activity activity, Context mContext,onItemClick listener,int flag) {
        this.stationset = stationset;
        this.activity = activity;
        this.mContext = mContext;
        this.mCallback = listener;
        this.flag = flag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_row, parent, false);
        //view.setOnClickListener(StationList.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Station station = stationset.get(position);
        holder.itemView.setTag(position);
        holder.Station.setText(station.getStation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 1) {
                    Source = stationset.get(position).getStation().toString();
                    mCallback.onSource(Source, "source");
                }
                else if (flag == 2){
                    Destination = stationset.get(position).getStation().toString();
                    mCallback.onSource(Destination, "destination");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stationset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Station;
        public MyViewHolder(View itemView) {
            super(itemView);
            Station = (TextView) itemView.findViewById(R.id.StationName);
        }
    }
}
