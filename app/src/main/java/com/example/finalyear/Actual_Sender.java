package com.example.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Actual_Sender extends AppCompatActivity {
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual__sender);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        password = bundle.getString("password");

    }

    public  void start(View v){
        Intent i =  new Intent(getApplicationContext(),VitalSignsProcess.class);
        i.putExtra("name",name);
        i.putExtra("password",password);
        startActivity(i);
    }
}
