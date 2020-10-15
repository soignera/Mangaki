package com.example.mangaki;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {


    @FormUrlEncoded
    @POST("vote/{id}")
    Call <Void> vote(
            @Header("Cookie") String token,
            @Field("csrfmiddlewaretoken") String csrfmiddlewaretoken,
            @Field("choice") String choice, @Path("id") int id);
    @FormUrlEncoded
    @POST("vote/1")
    Call <String> getSessionId(
            @Header("Cookie") String token,
            @Field("csrfmiddlewaretoken") String csrfmiddlewaretoken,
            @Field("choice") String choice);
    @GET("data/reco/als/anime.json")
    Call<List<Anime>> getRec(
            @Header("Cookie") String token
    );
    @FormUrlEncoded
    @POST("user/login/")
    Call<String> login(
            @Header("Cookie") String token,
            @Field("csrfmiddlewaretoken") String csrfmiddlewaretoken,
            @Field("login") String login,
            @Field("password") String password);


@GET("user/login/")
    Call<ResponseBody> getToken(@Header("Authorization") String cookie);
}
