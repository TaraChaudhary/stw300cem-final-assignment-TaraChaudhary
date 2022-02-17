package com.example.traffic_rule_and_sign_quiz_app.API;

import com.example.traffic_rule_and_sign_quiz_app.Model.Signal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Sign {

    @POST("sign")
    Call<Void> addSignal(@Body Signal signal);

    @GET("sign")
    Call<List<Signal>> getSign();
}
