package com.example.traffic_rule_and_sign_quiz_app.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.Signal;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter. ContactsViewHolder>{

    Context context;
    List<Signal> signalList;

    public MyAdapter(Context context, List<Signal> signalList) {
        this.context = context;
        this.signalList = signalList;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_signal,parent,false);
        return new ContactsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
        final Signal signal= signalList.get(position);
      String image= Url.imagePath + signal.getImage();


        Strick.StrictMode();

        try {
            URL url=new URL(image);
            holder.imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();

        }
        holder.Name.setText(signal.getName());
        holder.description.setText(signal.getDescription());

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                studentList.remove(position);
//                notifyItemRemoved(position);
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return signalList.size();
    }

public class ContactsViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProfile;
        TextView Name,description;
        ImageView delete;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.sign);
            Name = itemView.findViewById(R.id.namesignal);
            description = itemView.findViewById(R.id.signdescription);



        }
    }
}
