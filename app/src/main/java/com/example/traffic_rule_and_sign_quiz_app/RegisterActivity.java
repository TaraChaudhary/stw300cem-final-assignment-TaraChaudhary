package com.example.traffic_rule_and_sign_quiz_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traffic_rule_and_sign_quiz_app.API.User;
import com.example.traffic_rule_and_sign_quiz_app.Model.ImageResponse;
import com.example.traffic_rule_and_sign_quiz_app.Methods.LoginRegister;
import com.example.traffic_rule_and_sign_quiz_app.Methods.Strick;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.Services.CreateChannel;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    private LinearLayout linearName, linearGender,linearDob,linearEmail,linearPassword,linearSignup,linearimage;
    private TextView toolbarhead;
    private CircleImageView imgProfile;
    private Button btnStart,btnDob,btnGender,btnEmail,btnPassword,btnBack,btnSigup,btnlogin,btnimage;
    EditText fname,lname,phone,email,password,username;
    DatePicker dob;
    RadioGroup gender;
    String imagePath;
    private String imageName = "";
    private NotificationManagerCompat notificationManagerCompat;
    String udob,ufname,ulname,ugender,uphone,uemail,upassword,uusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnStart = findViewById(R.id.start);
        btnGender = findViewById(R.id.second);
        btnDob=findViewById(R.id.third);
        btnEmail=findViewById(R.id.fourth);
        btnPassword=findViewById(R.id.fifth);
        btnBack=findViewById(R.id.back);
        btnSigup=findViewById(R.id.btnsignup);
        btnlogin=findViewById(R.id.login);
        btnimage=findViewById(R.id.imagebtn);


        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        gender = findViewById(R.id.rgGender);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.mobile);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        linearName = findViewById(R.id.linear1);
        toolbarhead = findViewById(R.id.toolbarhead);
        linearGender = findViewById(R.id.linear2);
        linearDob=findViewById(R.id.linear3);
        linearEmail=findViewById(R.id.linear4);
        linearimage=findViewById(R.id.image);
        linearPassword=findViewById(R.id.linear5);
        linearSignup=findViewById(R.id.lsignup);
        imgProfile = findViewById(R.id.imgProfile);


        btnStart.setOnClickListener(this);
        btnGender.setOnClickListener(this);
        btnDob.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnPassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        gender.setOnCheckedChangeListener(this);
        btnSigup.setOnClickListener(this);
        btnimage.setOnClickListener(this);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.CreateChannel();

        dob = findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        c.set(2000, 11, 31);//Year,Mounth -1,Day
        dob.setMaxDate(c.getTimeInMillis());

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        if (i == R.id.rbMale) {
            ugender = "Male";
            Toast.makeText(this, "Male", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.rbFemale) {
            ugender = "Female";
            Toast.makeText(this, "Female", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.rbOther) {
            ugender = "Other";
            Toast.makeText(this, "Others", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.start:

                String ffname = fname.getText().toString();
                String llname = lname.getText().toString();
                if (TextUtils.isEmpty(ffname) || TextUtils.isEmpty(llname)) {

                    fname.setError("Enter First Name");
                    lname.setError("Enter Last Name");
                }else
                {
                    linearName.setVisibility(View.GONE);
                    linearGender.setVisibility(View.VISIBLE);
                    toolbarhead.setText("Gender");
                    ufname = fname.getText().toString();
                    ulname=lname.getText().toString();
                }
                break;


            case R.id.second:
                if (TextUtils.isEmpty(ugender)) {
                    Toast.makeText(this, "Select Your Gender", Toast.LENGTH_SHORT).show();
                }
                else {
                    linearDob.setVisibility(View.VISIBLE);
                    linearGender.setVisibility(View.GONE);
                    toolbarhead.setText("Birthday");

                }
                break;

            case R.id.third:
                linearEmail.setVisibility(View.VISIBLE);
                linearDob.setVisibility(View.GONE);
                toolbarhead.setText("Email");
                udob= dob.getDayOfMonth()+"/"+ (dob.getMonth() + 1)+"/"+dob.getYear();


                break;


            case R.id.fourth:
                String eemail = email.getText().toString();
                String mobile = phone.getText().toString();
                if (TextUtils.isEmpty(eemail)|| TextUtils.isEmpty(mobile)) {
                    email.setError("Enter Your Email");
                    phone.setError("Enter Your Phone Number");
                }else
                {
                    linearPassword.setVisibility(View.VISIBLE);
                    linearEmail.setVisibility(View.GONE);
                    toolbarhead.setText("Login");
                    uphone = phone.getText().toString();
                    uemail= email.getText().toString();
                }

                break;
            case R.id.fifth:
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(user)|| TextUtils.isEmpty(pass)) {
                    username.setError("Enter Your Username");
                    password.setError("Enter Your Phone password");
                }else {
                    linearPassword.setVisibility(View.GONE);
                    linearimage.setVisibility(View.VISIBLE);
                    toolbarhead.setText("Upload Profile");
                    uusername = username.getText().toString();
                    upassword= password.getText().toString();
                }
                break;
            case R.id.imagebtn:
                toolbarhead.setText("Terms & Policys");

                linearimage.setVisibility(View.GONE);
                linearSignup.setVisibility(View.VISIBLE);


                break;
            case R.id.back:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnsignup:

                Register();

                break;
        }

    }
    private void Register() {

        User_model user_model = new User_model(null,ufname,ulname,uphone,ugender,udob,uemail,uusername,upassword,imageName,"");

        LoginRegister loginRegister =new LoginRegister();

        if (loginRegister.registerUser(user_model))
        {
            DisplayNotification("Register success");
            Intent intent = new Intent(RegisterActivity.this  , LoginActivity.class );
            startActivity(intent);
            finish();
        }
        else {
            DisplayNotification("Registration failed");

        }
    }
    public void DisplayNotification(String message)
    {
        Notification notification=new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notification")
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManagerCompat.notify(1,notification);
    }


    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imgProfile.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
        askPermission();
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
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
            Toast.makeText(this, "Image added sucess", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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
                Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



