package com.example.user.vjti;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Emergency extends AppCompatActivity {
    TextView accident, handicap, medical;
    ImageButton accidentBtn, handicapBtn, medicalBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        accident = findViewById(R.id.accident);
        handicap = findViewById(R.id.handicap);
        medical = findViewById(R.id.medical);

        accidentBtn = findViewById(R.id.accidentBtn);
        handicapBtn = findViewById(R.id.handicapBtn);
        medicalBtn = findViewById(R.id.medicalBtn);

        accidentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "9769084086"));
                intent.putExtra("sms_body", "There is a Medical Accident in train reaching Kurla currently approximately 3 min 45 secs away");
            }
        });

        handicapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "9769084086"));
                intent.putExtra("sms_body", "There is a Handicap emergency in train reaching Ghatkoper currently approximately 3 min 03 secs away");
                startActivity(intent);

            }
        });

        medicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "9769084086"));
                intent.putExtra("sms_body", "There is a Medical emergency in train reaching Ghatkoper currently approximately 3 min 03 secs away");
                startActivity(intent);
            }
        });
    }
}
