package com.example.traffic_rule_and_sign_quiz_app.ui.Aboutus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUsFragment extends Fragment {

    String id, token;
    SharedPreferences prfs;
    CircleImageView imageView;
    User_model model;
    LoginRegister loginRegister = new LoginRegister();
    TextView textView;
    Button button;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);

        imageView=root.findViewById(R.id.post_profileimg);
        textView=root.findViewById(R.id.toolbarhead);
        button=root.findViewById(R.id.back);
        loadfirst();
        prfs = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        id = prfs.getString("id", "");
        token = prfs.getString("token", "");
        model= loginRegister.userDetail(id, token);

        Strick.StrictMode();

        try {
            URL url = new URL(Url.imagePath + model.getImage());
            imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    public void loadfirst()
    {
        textView.setText("Aboutus");
        button.setVisibility(View.GONE);
    }

}