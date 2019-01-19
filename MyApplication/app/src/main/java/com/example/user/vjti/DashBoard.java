package com.example.user.vjti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class DashBoard extends AppCompatActivity {

    TextView textView;
    Button proBtn;
    CardView tracking,emergency,buying;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        textView = findViewById(R.id.text);
        proBtn = findViewById(R.id.proBtn);
        emergency = findViewById(R.id.emergency);
        buying = findViewById(R.id.buying);
        tracking = findViewById(R.id.tracking);
        proBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conformation();
            }
        });

        buying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,InAppTicketing.class);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,Emergency.class);
                startActivity(intent);
            }
        });

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this,TrainList.class);
//                Toast.makeText(DashBoard.this, , Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        mPreferences= getSharedPreferences("user", MODE_PRIVATE);
        Boolean restoredData = mPreferences.getBoolean("isPro", false);
        if(restoredData)
        {
            proBtn.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            buying.setClickable(true);
            tracking.setClickable(true);
            buying.setAlpha(1);
            tracking.setAlpha(1);
        }
        else
        {
            buying.setClickable(false);
            tracking.setClickable(false);
        }
    }

    public void conformation() {
        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View conformationview = layoutInflater.inflate(R.layout.comformation, null);
        dialogbuilder.setView(conformationview);
        dialogbuilder.setTitle("CONFROMATION");
        final TextView confirm =  conformationview.findViewById(R.id.confirm);
        TextView cancel =  conformationview.findViewById(R.id.cancel);
        final AlertDialog conformationdialog = dialogbuilder.create();
        conformationdialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor EPreferences = getSharedPreferences("user", MODE_PRIVATE).edit();
               EPreferences.putBoolean("isPro",true);
               EPreferences.apply();
               Toast.makeText(DashBoard.this, "Transmitting Location For Live Tracking!", Toast.LENGTH_SHORT).show();
               conformationdialog.dismiss();
               textView.setVisibility(View.GONE);
               proBtn.setVisibility(View.GONE);
                buying.setClickable(true);
                tracking.setClickable(true);
                buying.setAlpha(1);
                tracking.setAlpha(1);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conformationdialog.dismiss();
            }
        });
    }
}
