package com.example.traffic_rule_and_sign_quiz_app.Fragment;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.traffic_rule_and_sign_quiz_app.API.User;
import com.example.traffic_rule_and_sign_quiz_app.Model.ImageResponse;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.Signal;
import com.example.traffic_rule_and_sign_quiz_app.R;
import com.example.traffic_rule_and_sign_quiz_app.Services.CreateChannel;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AddSignalFragment extends Fragment {
    Button button,back;
    String imagePath;
    private String imageName = "";
    EditText name,desc;
    LinearLayout linearLayout;
    private CircleImageView imgProfile;
    String name2,desc1 ;
    private NotificationManagerCompat notificationManagerCompat;

    public AddSignalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_signal, container, false);
        back=view.findViewById(R.id.back);
        name=view.findViewById(R.id.name);
        desc=view.findViewById(R.id.description);
        button=view.findViewById(R.id.imagebtn);
        imgProfile=view.findViewById(R.id.imgSignal);
        linearLayout=view.findViewById(R.id.linear);


        notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        CreateChannel channel = new CreateChannel(getContext());
        channel.CreateChannel();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageOnly();
                save();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                setFragment(new SignalFragment());
            }
        });
        return view;
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
        imgProfile.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
        askPermission();
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(),
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
            Toast.makeText(getContext(), "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            // saveImageOnly();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //   saveImageOnly();
            } else {
                Toast.makeText(getContext(), "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean setFragment(Fragment fragment){
        if(fragment != null)
        {
            getChildFragmentManager().beginTransaction().replace(R.id.linearlayout,fragment).commit();
            return true;
        }
        return false;
    }

    public void save()
    {
        name2=name.getText().toString();
        desc1=desc.getText().toString();
        Signal signal = new Signal(name2,desc1,imageName);

        LoginRegister loginRegister =new LoginRegister();

        if (loginRegister.addSignal(signal))
        {
            DisplayNotification();

        }
        else {
           DisplayNotification1();

        }

            }

    public void DisplayNotification()
    {
        Notification notification=new NotificationCompat.Builder(getContext(), CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notification")
                .setContentText("Sign add successfully")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManagerCompat.notify(1,notification);
    }

    public void DisplayNotification1()
    {
        Notification notification=new NotificationCompat.Builder(getContext(), CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notification")
                .setContentText("Something wrong")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManagerCompat.notify(1,notification);
    }


}