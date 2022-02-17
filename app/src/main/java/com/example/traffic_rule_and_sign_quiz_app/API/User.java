package com.example.traffic_rule_and_sign_quiz_app.API;

import com.example.traffic_rule_and_sign_quiz_app.Model.ImageResponse;
import com.example.traffic_rule_and_sign_quiz_app.Model.User_model;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface User {
    @POST("signup")
    Call<Void> registerUser(@Body User_model register);

    @POST("login")
    Call<User_model> userLogin(@Body User_model user_model);


    @Multipart
    @POST("upload/image")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("single/{id}")
    Call<User_model> getUserDetails(@Header("Authorization")String token, @Path("id") String id );

    @PUT("single/{id}")
    Call<Void> Updateuser(@Path("id") String id,@Body User_model userModel);

    @POST("logout")
    Call<Void> logout(@Header("Authorization")String token);

}
