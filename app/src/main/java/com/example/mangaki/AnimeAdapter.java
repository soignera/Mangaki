package com.example.mangaki;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.MyViewHolder> {
    String csrfmiddlewaretoken, token;
    private Context context;
    private List<Anime> data;
    RequestOptions options;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    public AnimeAdapter(Context context, List data, String token, String csrfmiddlewaretoken) {
        this.context = context;
        this.data = data;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        this.csrfmiddlewaretoken = csrfmiddlewaretoken;
        this.token = token;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.adapter_anime, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.category.setText(data.get(position).getCategory());
        final int id = data.get(position).getId();

        Glide.with(context).load(data.get(position).getPoster()).apply(options).into(holder.poster);

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl("https://mangaki.fr/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<Void> call = jsonPlaceHolderApi.vote(token ,csrfmiddlewaretoken, "dislike", id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            // Set Logged In statue to 'true'Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "=======onFailure: " + t.toString());
                        t.printStackTrace();
                        // Log error here since request failed
                    }
                });
            }


        });
        holder.neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl("https://mangaki.fr/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<Void> call = jsonPlaceHolderApi.vote(token ,csrfmiddlewaretoken, "neutral", id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            // Set Logged In statue to 'true'Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "=======onFailure: " + t.toString());
                        t.printStackTrace();
                        // Log error here since request failed
                    }
                });
            }


        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl("https://mangaki.fr/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<Void> call = jsonPlaceHolderApi.vote(token ,csrfmiddlewaretoken, "like", id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            // Set Logged In statue to 'true'Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "=======onFailure: " + t.toString());
                        t.printStackTrace();
                        // Log error here since request failed
                    }
                });
            }


        });
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl("https://mangaki.fr/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<Void> call = jsonPlaceHolderApi.vote(token ,csrfmiddlewaretoken, "favorite", id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            // Set Logged In statue to 'true'Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "=======onFailure: " + t.toString());
                        t.printStackTrace();
                        // Log error here since request failed
                    }
                });
            }


        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageButton like,favourite,dislike,neutral;
        TextView category;
        ImageView poster;
//        Button like;
        int id;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            poster = itemView.findViewById(R.id.thumbnail);
            like = (ImageButton) itemView.findViewById(R.id.like);
            favourite = itemView.findViewById(R.id.favorite_button);
            dislike = itemView.findViewById(R.id.dislike);
            neutral = itemView.findViewById(R.id.neutral_button);

        }
    }
}