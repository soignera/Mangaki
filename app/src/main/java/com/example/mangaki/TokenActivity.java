package com.example.mangaki;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TokenActivity extends AppCompatActivity {

    Button bt_login, bt_token;
    String cookie;
    String csrfmiddlewaretokenValue;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private static String token;
    String uname,upass, middleToken;
    com.example.mangaki.ResponseBody responseBody;
    EditText et_uname,et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        bt_login = findViewById(R.id.btn_login);
        bt_token = findViewById(R.id.btn_token);
        et_uname = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://mangaki.fr/") //specify base url here
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 login();
            }
        });

        bt_token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 getToken();
            }
        });
    }



         private void login(){

             uname = et_uname.getText().toString().trim();
             upass = et_pass.getText().toString().trim();
             responseBody = getToken();
             csrfmiddlewaretokenValue = responseBody.getFirst();
             token = responseBody.getSecond();

             Login login = new Login( token, uname,upass);
             Call<String> call = jsonPlaceHolderApi.login(token, csrfmiddlewaretokenValue, uname, upass);

             call.enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     if(response.isSuccessful()) {
                         Toast.makeText(TokenActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(TokenActivity.this,WelcomeActivity.class);
                         Log.i("--------------","i m dobe");
                         intent.putExtra("token",token);
                         intent.putExtra("csrfmiddlewareToken", csrfmiddlewaretokenValue);
                         Intent intent2 = new Intent(TokenActivity.this,AnimeAdapter.class);
                         intent2.putExtra("token",token);
                         intent2.putExtra("csrfmiddlewareToken", csrfmiddlewaretokenValue);
                         startActivity(intent);
                     }
                     else{
                         Toast.makeText(TokenActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Toast.makeText(TokenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
         }

         private com.example.mangaki.ResponseBody getToken(){
             Call<ResponseBody> call = jsonPlaceHolderApi.getToken(token);

             call.enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     if(response.isSuccessful()) {
                         try {
                             String html = response.body().string();
                             cookie = response.headers().get("Set-Cookie");
                             String[] csrftoken = cookie.split(";");
                             token = csrftoken[0];
                             Document document = Jsoup.parse(html);
                             Element csrfmiddlewaretokenInput = document.select("input[name=csrfmiddlewaretoken]").first();
                             csrfmiddlewaretokenValue = csrfmiddlewaretokenInput.attr("value");
                             Log.i("csrfmiddlewaretoken1=", csrfmiddlewaretokenValue.toString());

                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                     else{
                         Toast.makeText(TokenActivity.this, "Token invalid!", Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                         Toast.makeText(TokenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
             return new com.example.mangaki.ResponseBody(csrfmiddlewaretokenValue,token );
         }
}
