package com.example.traffic_rule_and_sign_quiz_app.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.traffic_rule_and_sign_quiz_app.Adapter.MyAdapter;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Model.Signal;

import com.example.traffic_rule_and_sign_quiz_app.R;

import java.util.ArrayList;
import java.util.List;

public class SignalFragment extends Fragment {

    Button button;
    private RecyclerView recyclerView;
    RelativeLayout relativeLayout;

    List<Signal>signals;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_signal, container, false);
        button= view.findViewById(R.id.addsignal);
        relativeLayout=view.findViewById(R.id.relative1);
        getsign();
        recyclerView = view.findViewById(R.id.list);
        MyAdapter Adapter = new MyAdapter( getContext(),signals);
        recyclerView.setAdapter(Adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
                setFragment(new AddSignalFragment());
            }
        });
        return  view;
    }

    public boolean setFragment(Fragment fragment){
        if(fragment != null)
        {
            getChildFragmentManager().beginTransaction().replace(R.id.relative,fragment).commit();
            return true;
        }
        return false;
    }

    public void getsign()
    {
        LoginRegister loginRegister=new LoginRegister();
        signals=loginRegister.Getsignal();

        if(signals==null)
        {
            Toast.makeText(getContext(), "NULL", Toast.LENGTH_SHORT).show();
        }

    }



}

