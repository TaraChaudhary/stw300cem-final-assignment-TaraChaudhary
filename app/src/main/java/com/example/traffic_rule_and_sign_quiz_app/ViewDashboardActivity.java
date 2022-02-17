package com.example.traffic_rule_and_sign_quiz_app;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.traffic_rule_and_sign_quiz_app.Fragment.ExampleFragment;
import com.example.traffic_rule_and_sign_quiz_app.Fragment.RoadFragment;
import com.example.traffic_rule_and_sign_quiz_app.Fragment.SignalFragment;
import com.example.traffic_rule_and_sign_quiz_app.Fragment.TempFragment;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;

import com.example.traffic_rule_and_sign_quiz_app.Practice.QuestionActivity;
import com.example.traffic_rule_and_sign_quiz_app.Practice.ResultActivity;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewDashboardActivity extends Fragment {
    private RecyclerView view;
    LinearLayout practice1,example1,score1, linearLayout,map1,road,signal1,help1;
    String id, token;
    SharedPreferences prfs;
    CircleImageView imageView;
    User_model model;
    LoginRegister loginRegister = new LoginRegister();

    TextView textView;
Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =inflater.inflate(R.layout.activity_view_dashboard, container, false);

    linearLayout=root.findViewById(R.id.lineara);
    textView=root.findViewById(R.id.toolbarhead);
        practice1=root.findViewById(R.id.practice);
        example1=root.findViewById(R.id.example);
        score1=root.findViewById(R.id.score);
        map1=root.findViewById(R.id.maps);
        road=root.findViewById(R.id.roadrule);
        signal1=root.findViewById(R.id.signal);
        help1=root.findViewById(R.id.help);
        button=root.findViewById(R.id.back);
        imageView=root.findViewById(R.id.post_profileimg);

        prfs = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        id = prfs.getString("id", "");
        token = prfs.getString("token", "");
        model= loginRegister.userDetail(id, token);

        loadfirst();

        Strick.StrictMode();

        try {
            URL url = new URL(Url.imagePath + model.getImage());
            imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                textView.setText("Temp");
                button.setVisibility(View.VISIBLE);

                setFragment(new TempFragment());
            }
        });
        practice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),QuestionActivity.class);
                startActivity(intent);


            }
        });

        example1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   linearLayout.setVisibility(View.GONE);
                   textView.setText("Example");
                   button.setVisibility(View.VISIBLE);

                setFragment(new ExampleFragment());

            }
        });

        score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score=new Intent(getActivity(), ResultActivity.class);
                startActivity(score);
            }
        });

        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(getActivity(), MapActivity.class);
                startActivity(m);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back =new Intent(getActivity(),DashboardActivity.class);
                startActivity(back);
            }
        });
        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                textView.setText("Road rule");
                button.setVisibility(View.VISIBLE);

                setFragment(new RoadFragment());
            }
        });

        signal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                textView.setText("Sign");
                button.setVisibility(View.VISIBLE);

                setFragment(new SignalFragment());
            }
        });
        return root;


    }
    public boolean setFragment(Fragment fragment){
        if(fragment != null)
        {
            getChildFragmentManager().beginTransaction().replace(R.id.linearlayout,fragment).commit();
            return true;
        }
        return false;
    }
    public void loadfirst()
    {
        button.setVisibility(View.GONE);
    }




}