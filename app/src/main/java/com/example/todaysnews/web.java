package com.example.todaysnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String a  = getIntent().getStringExtra("URL");
        Toast.makeText(this,a,Toast.LENGTH_SHORT).show();

    }
}