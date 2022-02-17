package com.example.traffic_rule_and_sign_quiz_app.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traffic_rule_and_sign_quiz_app.R;

public class TempFragment extends Fragment {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener listener;
    TextView textView;


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view =inflater.inflate(R.layout.fragment_temp, container, false);
            textView= view.findViewById(R.id.helps);


            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

            sensorTemp();

            return  view;


        }

    public void sensorTemp(){
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                textView.setText(event.values[0]+"C");
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
            Toast.makeText(getContext(),
                    "Requested sensor is not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

        public boolean setFragment(Fragment fragment){
            if(fragment != null)
            {
                getChildFragmentManager().beginTransaction().replace(R.id.relative,fragment).commit();
                return true;
            }
            return false;
        }





    }

