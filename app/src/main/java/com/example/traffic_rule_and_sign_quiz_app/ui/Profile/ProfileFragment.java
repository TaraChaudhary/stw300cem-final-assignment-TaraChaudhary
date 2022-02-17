package com.example.traffic_rule_and_sign_quiz_app.ui.Profile;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.traffic_rule_and_sign_quiz_app.LoginActivity;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.Services.CreateChannel;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;
import com.example.traffic_rule_and_sign_quiz_app.ViewDashboardActivity;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    String id, token;
    SharedPreferences prfs;
    User_model model;
    LoginRegister loginRegister = new LoginRegister();
    ImageView edit;
    TextView firstname, lastname, dobtext, gendertext, emailtext, phonetext, username,fname;
   CircleImageView imageView;
   RelativeLayout relativeLayout;
   Button back,logout;
    private NotificationManagerCompat notificationManagerCompat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        firstname = root.findViewById(R.id.firstname);
        lastname = root.findViewById(R.id.lastname);
        dobtext = root.findViewById(R.id.dob1);
        gendertext = root.findViewById(R.id.gender);
        username = root.findViewById(R.id.username);
        emailtext = root.findViewById(R.id.email1);
        phonetext = root.findViewById(R.id.phone1);
        fname=root.findViewById(R.id.fname);
        edit=root.findViewById(R.id.edit);
        logout=root.findViewById(R.id.logout);
        back=root.findViewById(R.id.back);
        imageView = root.findViewById(R.id.post_profileimg);
        relativeLayout=root.findViewById(R.id.layout1);

        notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        CreateChannel channel = new CreateChannel(getContext());
        channel.CreateChannel();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relativeLayout.setVisibility(View.GONE);
                setFragment(new UpdateFragment());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                setFragment(new ViewDashboardActivity());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRegister loginRegister=new LoginRegister();

               if( loginRegister.logout(token))
               {

                   DisplayNotification("Logout Successfully");
                   Intent intent=new Intent(getActivity(), LoginActivity.class);
                   startActivity(intent);

               }
               else {
                   DisplayNotification("Logout Failed");
               }
            }
        });

        prfs = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        id = prfs.getString("id", "");
        token = prfs.getString("token", "");
     //   Toast.makeText(getContext(), token + id, Toast.LENGTH_SHORT).show();
        model = loginRegister.userDetail(id, token);
        firstname.setText(model.getFirstname());
        username.setText(model.getUsername());
        fname.setText(model.getFirstname()+" "+model.getLastname());
        gendertext.setText(model.getGender());
        emailtext.setText(model.getEmail());
        lastname.setText(model.getLastname());
        phonetext.setText(model.getPhone());
        dobtext.setText(model.getDob());


        Strick.StrictMode();

        try {
            URL url = new URL(Url.imagePath + model.getImage());
            imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }
    public boolean setFragment(Fragment fragment){
        if(fragment != null)
        {
            getChildFragmentManager().beginTransaction().replace(R.id.layout,fragment).commit();
            return true;
        }
        return false;
    }
    public void DisplayNotification(String message)
    {
        Notification notification=new NotificationCompat.Builder(getContext(), CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notification")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManagerCompat.notify(1,notification);
    }


}