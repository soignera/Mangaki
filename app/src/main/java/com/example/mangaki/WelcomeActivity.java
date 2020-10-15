package com.example.mangaki;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author soignera
 */
public class WelcomeActivity extends AppCompatActivity {

    Button logoutBT;
    Retrofit retrofit;
    String token,csrfmiddlewareToken, cookie, idsession, newtoken;
    String sessionIdValue = "";
    private String URL = "https://mangaki.fr/api/cards/anime/top?format=json";
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<Anime> list = new ArrayList<>();
    private RecyclerView recyclerView;
    JsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getIntent().getStringExtra("token");
        csrfmiddlewareToken = getIntent().getStringExtra("csrfmiddlewareToken");
        setContentView(R.layout.activity_welcome);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle_view);
        jsoncall();
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
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        String sessionidValue = getSessionId();

        logoutBT = findViewById(R.id.logoutBT);


        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void jsoncall() {
        arrayRequest = new JsonArrayRequest(URL, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Anime anime = new Anime();
                        anime.setTitle(jsonObject.getString("title"));
                        anime.setCategory(jsonObject.getString("category"));
                        anime.setPoster(jsonObject.getString("poster"));
                        anime.setId(jsonObject.getInt("id"));

                        list.add(anime);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(WelcomeActivity.this, getString(R.string.list) + String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                Toast.makeText(WelcomeActivity.this, list.get(1).toString(), Toast.LENGTH_SHORT).show();
                setrecycleview(list, token,  csrfmiddlewareToken);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(WelcomeActivity.this);
        requestQueue.add(arrayRequest);
    }

    private void setrecycleview(List<Anime> list, String newtoken,  String csrfmiddlewareToken) {
        AnimeAdapter adapter = new AnimeAdapter(this, list, newtoken,  csrfmiddlewareToken);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Logout
     * TODO: Please modify according to your need it is just an example
     */
    public void logout() {
//        Retrofit retrofit = RetrofitClient.getClient();
//        LoginServices loginServices = retrofit.create(LoginServices.class);
//        Call<Void> logout = loginServices.logout();
//
//        logout.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.code() == 200) {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("TAG", "=======onFailure: " + t.toString());
//                t.printStackTrace();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SortID 1 -> Popularity
        //SortID 2 -> Top rated
        switch (item.getItemId()) {
            case R.id.sort_by_popularity:
                    idsession = getSessionId();
                    Log.i("===kapec", idsession);
                break;
            case R.id.sort_by_top:
                newtoken = token+"; "+idsession;
                setrecycleview(list,newtoken,csrfmiddlewareToken);
                break;
            case R.id.sort_by_favorites:
                    getRec();
                break;
        }

        return super.onOptionsItemSelected(item);

    }



public void getRec(){


    Call<List<Anime>> call = jsonPlaceHolderApi.getRec(token+"; "+idsession);

    call.enqueue(new Callback<List<Anime>>() {
        @Override
        public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
            if(!response.isSuccessful()){
                Toast.makeText(WelcomeActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
            }

                List<Anime> animes = response.body();

                for(Anime anime : animes){
                    String content = "";
                    content += "id: "+anime.getId()+"\n";
                    content += "category: "+anime.getCategory()+"\n";
                    content += "title: "+anime.getTitle()+"\n";
                    content += "poster: "+anime.getPoster()+"\n\n";

                    Log.i("====content",content);
                    setrecycleview(animes, newtoken, csrfmiddlewareToken);
                }

        }

        @Override
        public void onFailure(Call<List<Anime>> call, Throwable t) {
            Toast.makeText(WelcomeActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}

    public String getSessionId(){

        Call<String> call = jsonPlaceHolderApi.getSessionId(token, csrfmiddlewareToken, "like");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    cookie = response.headers().get("Set-Cookie");
                    String[] csrftoken = cookie.split(";");
                    sessionIdValue = csrftoken[0];
                    Log.i("=================ку",sessionIdValue);

                }
                else{
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("=================",t.getMessage());

            }
        });
        return sessionIdValue;
    }

}
