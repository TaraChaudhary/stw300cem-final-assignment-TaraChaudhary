package com.example.traffic_rule_and_sign_quiz_app.Practice;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traffic_rule_and_sign_quiz_app.DashboardActivity;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.Practice.ResultActivity;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionActivity extends AppCompatActivity {
    TextView tv,text1;
    Button submitbutton, quitbutton,button;
    SearchView searchView;
    RadioGroup radio_g;
    CircleImageView imageView;
    RadioButton rb1, rb2, rb3, rb4;

    String id, token;
    SharedPreferences prfs;
    User_model model;
    LoginRegister loginRegister = new LoginRegister();

    String questions[] = {
            "What is the eligible age of getting motorcycle driving licence?",
            "Why in ambulance 'Ambulance' is written opposite? Due to",
            "What shouldnot be in driver to take driving licence?",
            "Which are the exam to get driving licence?",
            "What is the maximum speed of scooter?",
            "What is the maximum speed of Motorcycle?",
            "In which places does a international driving licence holder can drive in Nepal?",
            "After how many years does the licence expired?",
            "Which is the most dangerous vehicle to drive?",
            "How many total person can be seated in Motorcycle?"
    };
    String answers[] = {"16 years", "Seen right in other's looking glass", "Nightblindness", "Both 1 and 2",
            "40 k.m", "50 k.m", "All over Nepal", "5 years", "Motorcycle", "int"};
    String opt[] = {
            "21 years", "16 years", "12 years", "25 years",
            "Speedyness", "No one reconize", "Patient delevery van", "Seen right in other's looking glass",
            "Cancer", "Nightblindness", "Wear glasses", "Donot have hair",
            "Trail", "Written", "Nonone", "Both 1 and 2",
            "40 k.m", "60 k.m", "20 k.m", "80 k.m",
            "40 k.m", "60 k.m", "50 k.m", "80 k.m",
            "Kathmandu", "Butwal", "Itahari", "All over Nepal",
            "1 year", "2 years", "5 years", "3 years",
            "Motorcycle", "Bus", "Car", "Truck",
            "1 person", "2 person", "3 person", "5 person"
    };
    int flag = 0;
    public static int marks = 0, correct = 0, wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final TextView score = (TextView) findViewById(R.id.textView4);
        TextView textView = (TextView) findViewById(R.id.DispName);
        text1=findViewById(R.id.toolbarhead);
        imageView=findViewById(R.id.post_profileimg);
        button=findViewById(R.id.back);






        submitbutton = (Button) findViewById(R.id.button3);
        quitbutton = (Button) findViewById(R.id.buttonquit);
        tv = (TextView) findViewById(R.id.tvque);

        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);


        prfs = getApplicationContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        id = prfs.getString("id", "");
        token = prfs.getString("token", "");
        model = loginRegister.userDetail(id, token);
        Strick.StrictMode();

        try {
            URL url = new URL(Url.imagePath + model.getImage());
            imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadfirst();
        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back =new Intent(QuestionActivity.this, DashboardActivity.class);
                startActivity(back);
            }
        });
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int color = mBackgroundColor.getColor();
                //mLayout.setBackgroundColor(color);

                if (radio_g.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
//                Toast.makeText(getApplicationContext(), ansText, Toast.LENGTH_SHORT).show();
                if (ansText.equals(answers[flag])) {
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }

                flag++;

                if (score != null)
                    score.setText("" + correct);

                if (flag < questions.length) {
                    tv.setText(questions[flag]);
                    rb1.setText(opt[flag * 4]);
                    rb2.setText(opt[flag * 4 + 1]);
                    rb3.setText(opt[flag * 4 + 2]);
                    rb4.setText(opt[flag * 4 + 3]);
                } else {
                    marks = correct;
                    Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(in);
                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadfirst()
    {
        text1.setText("Practice");


    }

}