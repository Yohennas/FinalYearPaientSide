package com.example.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Login extends AppCompatActivity {

    EditText name, password;
    DataBase dataBase;
    Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.nameEdt);
        password = findViewById(R.id.passwordEdt);
        cache = new Cache(this);

    }

    public void Login(View v){
        //check if in data base first
        String Strname = name.getText().toString().trim();
        String Strpassword = password.getText().toString().trim();
        try {
            int version = Integer.parseInt(cache.getStringProperty("versionNumber"));

            dataBase = new DataBase(this, Strname, version,Strpassword);
            //boolean check = dataBase.checkTable(); // this doesnt work
            String CHKname = null;

            CHKname = dataBase.stringValue("name");

            if(CHKname.trim().equals(Strname)) {
                try {
                    String foundPassword = dataBase.stringValue("password").trim();

                    if (Strpassword.equals(foundPassword)) {
                        Intent i = new Intent(getApplicationContext(), Mode_Pick.class);
                        i.putExtra("name", Strname);
                        i.putExtra("password", Strpassword);
                        startActivity(i);
                    } else {
                        notifyUser("Username and/or password incorrect");
                    }
                }catch (Exception e){notifyUser("error pls make new account");}
            }
            else{
                notifyUser("Username and/or password incorrect");
            }
        }catch (Exception e){
            notifyUser("Username and/or password incorrect");
        }
    }

    private void notifyUser(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
