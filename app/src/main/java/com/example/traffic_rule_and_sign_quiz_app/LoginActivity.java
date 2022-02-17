package com.example.traffic_rule_and_sign_quiz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.Services.Broadcast;
import com.example.traffic_rule_and_sign_quiz_app.Services.CreateChannel;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

public class LoginActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;
    private ImageButton btnLogin;
    private Button Signup;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener listener;
    private String Username, Password;
    private CheckBox remember;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private NotificationManagerCompat notificationManagerCompat;

    Broadcast broadCast = new Broadcast(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        editUsername = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.signin);

        Signup = findViewById(R.id.signup);
        remember = findViewById(R.id.checkbox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editUsername.setText(loginPreferences.getString("username", ""));
            editPassword.setText(loginPreferences.getString("password", ""));
            remember.setChecked(true);
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if(event.values[0] <= 5){
                    Username = editUsername.getText().toString();
                    Password = editPassword.getText().toString();
                    if (validate()) {

                        Login();
                    }

                }
                else {
                    //any method

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        if(sensor != null){
            sensorManager
                    .registerListener(listener,sensor,
                            SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this,
                    "Requested sensor is not available",
                    Toast.LENGTH_SHORT).show();
        }



        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.CreateChannel();



        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = editUsername.getText().toString();
                Password = editPassword.getText().toString();
                if (validate()) {

                    Login();
                }

            }
        });
    }


            private void Login() {
                User_model user = new User_model(Username, Password);
                // userLogin(user);

                LoginRegister loginRegister = new LoginRegister();

                if (loginRegister.userLogin(user)) {
                    SharedPreferences preferences = getSharedPreferences("session", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("id", Url.id);
                    editor.putString("token", Url.token);

                    editor.commit();
                    if (remember.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", Username);
                        loginPrefsEditor.putString("password", Password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    DisplayNotification("Login Successfully");
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    DisplayNotification("Login Failed");
                    Toast.makeText(LoginActivity.this, "Username and password wrong", Toast.LENGTH_SHORT).show();

                }

            }

    public void DisplayNotification(String message)
    {
        Notification notification=new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notification")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManagerCompat.notify(1,notification);
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadCast,intentFilter);
    }



    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadCast);
    }





            private boolean validate() {
                if (TextUtils.isEmpty(Username)) {
                    editUsername.setError("Please Enter Username");
                    editUsername.requestFocus();
                    return false;

                }
                if (TextUtils.isEmpty(Password)) {
                    editPassword.setError("Please Enter Passward");
                    editPassword.requestFocus();
                    return false;
                }
                return true;
            }


}




