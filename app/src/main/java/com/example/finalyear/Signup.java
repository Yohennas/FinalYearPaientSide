package com.example.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Signup extends AppCompatActivity {

    EditText name, password, height, weight, age;
    Spinner sex;
    Pateint pateint;
    DataBase dataBase;
    Cache cache;
    int version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.nameEdt);
        password = findViewById(R.id.passwordEdt);
        height = findViewById(R.id.heightEdt);
        weight = findViewById(R.id.weightEdt);
        sex = findViewById(R.id.sexSpinner);
        age = findViewById(R.id.ageEdt);
        cache = new Cache(this);

        String[] sexSpinner = {"Male","Female"};

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,sexSpinner);

        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(sexAdapter);

    }

    public void Signup(View v){
        //add to database
       // try {
            //add surname
            String Strname = name.getText().toString();
            String Strpassword = password.getText().toString();
            String Strheight=null;
            String Strweight=null;
            String Strage=null;

        try {
                Strheight = height.getText().toString();
                Strweight = weight.getText().toString();
                Strage = age.getText().toString();

                int height = Integer.parseInt(Strheight);
                int weight = Integer.parseInt(Strweight);
                int age = Integer.parseInt(Strage);

            }catch(Exception e){
                notifyUser("Please input number values for height, weight and age");
            }


            String Strsex = sex.getSelectedItem().toString();

            if(Strpassword.length()<8){
                notifyUser("Password must be 8 characters long");
            }
            else {
                pateint = new Pateint(Strname, Strpassword, Strage, Strheight, Strweight, Strsex,
                        "", "", "", "");

                String value = cache.getStringProperty("versionNumber");
                if (value != null) {
                    version = Integer.parseInt(value) + 1;
                    cache.setStringProperty("versionNumber", Integer.toString(version));
                } else {
                    version = 1;
                    cache.setStringProperty("versionNumber", Integer.toString(version));
                }

                dataBase = new DataBase(this, Strname, version, Strpassword); // doesn't create new table
                dataBase.addInstance(pateint);


                String CHKname = null;

                try {
                    CHKname = dataBase.stringValue("name");

                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                }
                notifyUser("Welcome " + CHKname);
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
       // }catch (Exception e){notifyUser("please fill in sections correctly");}
    }

    private void notifyUser(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Signup.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
