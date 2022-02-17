package com.example.traffic_rule_and_sign_quiz_app.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.traffic_rule_and_sign_quiz_app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExampleFragment extends Fragment {

    Button button;

    public ExampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_example, container, false);

        return  view;
    }

}
