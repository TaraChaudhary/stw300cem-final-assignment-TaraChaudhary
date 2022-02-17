package com.example.traffic_rule_and_sign_quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.INotificationSideChannel;
import android.widget.ProgressBar;

public class ProgressActivity extends AppCompatActivity {
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        progressBar=findViewById(R.id.progress_circular);

    }
}
