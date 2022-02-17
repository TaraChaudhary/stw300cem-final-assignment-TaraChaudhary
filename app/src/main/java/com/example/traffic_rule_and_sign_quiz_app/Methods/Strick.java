package com.example.traffic_rule_and_sign_quiz_app.Methods;

public class Strick {
    public static void StrictMode()
    {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
}
