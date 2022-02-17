package com.example.traffic_rule_and_sign_quiz_app.Methods;

import android.util.Log;

import com.example.traffic_rule_and_sign_quiz_app.API.Sign;
import com.example.traffic_rule_and_sign_quiz_app.API.User;
import com.example.traffic_rule_and_sign_quiz_app.Model.Signal;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;
import com.example.traffic_rule_and_sign_quiz_app.Url.Url;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoginRegister {

    boolean isloggedIn,RegisterIn,Updatein,singnalin,getsignal=false;
     User user= Url.getInstance().create(User.class);
    Sign sig= Url.getInstance().create(Sign.class);
    public boolean userLogin(User_model user_model)
    {

        Call<User_model> userCall=user.userLogin(user_model);
      Strick.StrictMode();

        try {
            Response<User_model>loginResponse=userCall.execute();

            if (loginResponse.isSuccessful())
            {
                isloggedIn=true;
                Url.id = loginResponse.body().get_id();


                Url.token += loginResponse.body().getToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
           // Log.d("Myex:", e.getMessage());
        }
        return isloggedIn;
    }


    public User_model userDetail(String id,String Token)
    {
        User_model user_model = null;
    //    System.out.println(id);
    //    System.out.println(Token);
        Call<User_model> userCall=user.getUserDetails(Token,id);
        Strick.StrictMode();

        try {
            Response<User_model>loginResponse=userCall.execute();

            if (loginResponse.isSuccessful())
            {
               user_model=loginResponse.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Myex:", e.getMessage());
        }
        return user_model;
    }
    public boolean updateuser(String id,User_model userModel)
    {

        Call<Void> userCall=user.Updateuser(id,userModel);
        Strick.StrictMode();

        try {
            Response<Void>updateResponse=userCall.execute();
            if (updateResponse.isSuccessful())
            {
                Updatein=true;

                // Url.token +=loginResponse.body().getToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Myex:", e.getMessage());
        }
        return Updatein;
    }
    public boolean registerUser(User_model register)
    {

        Call<Void> userCall=user.registerUser(register);
        Strick.StrictMode();

        try {
            Response<Void>registerResponse=userCall.execute();
            if (registerResponse.isSuccessful())
            {
                RegisterIn=true;

                // Url.token +=loginResponse.body().getToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Myex:", e.getMessage());
        }
        return RegisterIn;
    }

    public boolean addSignal(Signal signal)
    {

        Call<Void> userCall=sig.addSignal(signal);
        Strick.StrictMode();

        try {
            Response<Void>signalResponse=userCall.execute();
            if (signalResponse.isSuccessful())
            {
                singnalin=true;

                // Url.token +=loginResponse.body().getToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Myex:", e.getMessage());
        }
        return singnalin;
    }
    public  boolean logout(String token)
    {

        Call<Void> userCall=user.logout(token);
        Strick.StrictMode();

        try {
            Response<Void>signalResponse=userCall.execute();
            if (signalResponse.isSuccessful())
            {
                singnalin=true;

                // Url.token +=loginResponse.body().getToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Myex:", e.getMessage());
        }
        return singnalin;
    }
    public List<Signal> Getsignal()
    {
        List<Signal> signalList=null;

        Call<List<Signal>> userCall=sig.getSign();
        Strick.StrictMode();

        try {
            Response<List<Signal>>getresponse=userCall.execute();
            if (getresponse.isSuccessful())
            {
              signalList=getresponse.body();

                // Url.token +=loginResponse.body().getToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Myex:", e.getMessage());
        }
        return signalList;
    }




}
