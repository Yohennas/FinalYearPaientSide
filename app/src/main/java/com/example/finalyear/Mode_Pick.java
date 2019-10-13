package com.example.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Mode_Pick extends AppCompatActivity {
    String name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode__pick);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        password = bundle.getString("password");
    }

    public void sendRNG(View v){
        Intent i =  new Intent(getApplicationContext(),RNG_Sender.class);
        i.putExtra("name",name);
        i.putExtra("password",password);
        startActivity(i);
    }

    public void sendActual(View v){
        Intent i =  new Intent(getApplicationContext(),Actual_Sender.class);
        i.putExtra("name",name);
        i.putExtra("password",password);
        startActivity(i);
    }

    public void viewRecords(View v){
        Intent i =  new Intent(getApplicationContext(),ViewRecords.class);
        i.putExtra("name",name);
        i.putExtra("password",password);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Mode_Pick.this, Login.class);
        startActivity(i);
        finish();
    }
}
