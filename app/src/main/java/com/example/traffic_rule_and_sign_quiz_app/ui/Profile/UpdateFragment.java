package com.example.traffic_rule_and_sign_quiz_app.ui.Profile;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.CursorLoader;

import com.example.traffic_rule_and_sign_quiz_app.API.User;
import com.example.traffic_rule_and_sign_quiz_app.LoginActivity;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.ImageResponse;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.RegisterActivity;
import com.example.traffic_rule_and_sign_quiz_app.Services.CreateChannel;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class UpdateFragment extends Fragment {
    String id, token;
    SharedPreferences prfs;
    User_model model;
    LoginRegister loginRegister = new LoginRegister();
    TextView fname1,user;
    EditText firstname1, lastname1, dobtext, gendertext, emailtext, phonetext, username;
    CircleImageView imageView;
    RelativeLayout relativeLayout;
    Button back,update;
    String firstname,lastname,email,phone,user1;
    String imagePath;
    private String imageName = "";
    private NotificationManagerCompat notificationManagerCompat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_update, container, false);
        firstname1 = root.findViewById(R.id.first);
        lastname1 = root.findViewById(R.id.last);
        user=root.findViewById(R.id.username);
        username = root.findViewById(R.id.username1);
        emailtext = root.findViewById(R.id.email1);
        phonetext = root.findViewById(R.id.phone1);
        fname1 = root.findViewById(R.id.fname1);
        //     edit=root.findViewById(R.id.edit);
        imageView = root.findViewById(R.id.post_profileimg);
        relativeLayout = root.findViewById(R.id.relative1);
        back=root.findViewById(R.id.back);
        update=root.findViewById(R.id.signup);

        notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        CreateChannel channel = new CreateChannel(getContext());
        channel.CreateChannel();



        prfs = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        id = prfs.getString("id", "");
        token = prfs.getString("token", "");
        //   Toast.makeText(getContext(), token + id, Toast.LENGTH_SHORT).show();
        model = loginRegister.userDetail(id, token);
        firstname1.setText(model.getFirstname());
        username.setText(model.getUsername());
        user.setText(model.getUsername());
        fname1.setText(model.getFirstname() + " " + model.getLastname());
//        gendertext.setText(model.getGender());
        emailtext.setText(model.getEmail());
        lastname1.setText(model.getLastname());
        phonetext.setText(model.getPhone());
   //     dobtext.setText(model.getDob());


        Strick.StrictMode();

        try {
            URL url = new URL(Url.imagePath + model.getImage());
            imageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                setFragment(new ProfileFragment());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Update();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        return root;
    }

    public boolean setFragment(Fragment fragment) {
        if (fragment != null) {
            getChildFragmentManager().beginTransaction().replace(R.id.relative, fragment).commit();
            return true;
        }
        return false;
    }

    private void Update() {

        user1=username.getText().toString();
        firstname=firstname1.getText().toString();
        lastname=lastname1.getText().toString();
        phone=phonetext.getText().toString();
        email=emailtext.getText().toString();


        User_model user_model= new User_model(firstname,lastname,phone,email,user1,imageName);

        LoginRegister loginRegister =new LoginRegister();

        if (loginRegister.updateuser(id,user_model))
        {
            DisplayNotification("Update success");
           // Toast.makeText(getActivity(), "User updated", Toast.LENGTH_SHORT).show();


        }
        else {
            DisplayNotification("Update failed");
          //  Toast.makeText(getActivity(), "Some thing missing", Toast.LENGTH_SHORT).show();

        }
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(getContext(), "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imageView.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
        askPermission();
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {

        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        User user = Url.getInstance().create(User.class);
        Call<ImageResponse> responseBodyCall = user.uploadImage(body);
        Strick.StrictMode();
        //Synchronous methid
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(getContext(), "Image upload", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
             saveImageOnly();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //   saveImageOnly();
            }
            else {
                Toast.makeText(getActivity(), "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void DisplayNotification( String message)
    {
        Notification notification=new NotificationCompat.Builder(getContext(), CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notification")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManagerCompat.notify(1,notification);
    }


}



