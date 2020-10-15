package com.example.mangaki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void btn_token(View view){
        Intent intent = new Intent(MainActivity.this,TokenActivity.class);
        startActivity(intent);
    }
}
