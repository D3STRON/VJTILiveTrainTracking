package com.example.user.vjti;

import android.os.Handler;
import android.widget.TextView;

class LocationUpdate {
    public int t = 0;
    Runnable updater;
    void updateTime(final TextView textView) {
        final Handler timerHandler = new Handler();
        updater = new Runnable() {
            @Override
            public void run() {
                textView.setText(Integer.toString(t));
                timerHandler.postDelayed(updater, 1000);
                t = (t + 100) % 500;
            }
        };
        timerHandler.post(updater);
    }
}
