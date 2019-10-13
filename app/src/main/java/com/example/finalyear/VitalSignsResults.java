package com.example.finalyear;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class VitalSignsResults extends AppCompatActivity {

    private String user,Date;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    int VBP1,VBP2,VRR,VHR,VO2;
    String message, name, password;
    RSASender rsa;
    String[] algorithmNames;
    int algorithmNumber;
    BigInteger send;
    String FinalIP, algorithmChosen;
    boolean connected;
    Algorithm algorithm;
    DataBase dataBase;
    EditText ip;
    Random rand;
    NormalValues normalValues;
    Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs_results);
        Bundle bundle = getIntent().getExtras();
        Date = df.format(today);
        ip = findViewById(R.id.ip);
        TextView VSRR = (TextView) this.findViewById(R.id.RRV);
        TextView VSBPS = (TextView) this.findViewById(R.id.BP2V);
        TextView VSHR = (TextView) this.findViewById(R.id.HRV);
        TextView VSO2 = (TextView) this.findViewById(R.id.O2V);

        TextView VSRRNorm = (TextView) this.findViewById(R.id.RRVNorm);
        TextView VSBPSNorm = (TextView) this.findViewById(R.id.BP2VNorm);
        TextView VSHRNorm = (TextView) this.findViewById(R.id.HRVNorm);
        TextView VSO2Norm = (TextView) this.findViewById(R.id.O2VNorm);

        cache = new Cache(this);
        int version = Integer.parseInt(cache.getStringProperty("versionNumber"));


        //add headings
        if (bundle != null) {
            name = bundle.getString("name");
            password = bundle.getString("password");
            VRR = bundle.getInt("breath");
            VHR = bundle.getInt("bpm");
            VBP1 = bundle.getInt("SP");
            VBP2 = bundle.getInt("DP");
            VO2 = bundle.getInt("O2R");
            VSRR.setText(String.valueOf(VRR)+" breaths");
            VSHR.setText(String.valueOf(VHR)+ "bpm");
            VSBPS.setText(String.valueOf(VBP1+" / "+VBP2)+" mmHg");
            VSO2.setText(String.valueOf(VO2)+"%");
        }

        dataBase = new DataBase(getApplicationContext(),name,version,password);
//        int value = dataBase.checkSize();
  //      Log.e("cake",value+"");
        rand = new Random();
        algorithmNames = new String[]{"Blowfish", "AES", "RC4"};
        //adds to database
        String password = null;
        String age = null;
        String height = null;
        String weight = null;
        String sex = null;
        try {
            password = dataBase.stringValue("password");
            age = dataBase.stringValue("age");
            height = dataBase.stringValue("height");
            weight = dataBase.stringValue("weight");
            sex = dataBase.stringValue("sex");
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
        normalValues = new NormalValues(age, height,weight,sex);

        String heartRate = normalValues.calculateHeartRate();
        String bloodPressure = normalValues.calculateBloodPressure();
        String bloodOxygen = normalValues.calculateBloodOxygen();
        String respirationRate = normalValues.calculateRespirationRate();


        VSHRNorm.setText(heartRate);
        VSBPSNorm.setText(bloodPressure);
        VSO2Norm.setText(bloodOxygen);
        VSRRNorm.setText(respirationRate);

        Pateint pateint = new Pateint(name,password,age,height,weight,sex,VBP1+"/"+VBP2,""+VRR,""+VO2,""+VHR);
        //encrypt patient before adding
        dataBase.addInstance(pateint);

        message = VBP1+"/"+VBP2+" "+VHR+" "+VO2+" "+VRR+" "+pateint.toString()+" "+version;
        Log.e("message", message);
        rsa = new RSASender();
        algorithmNumber = rand.nextInt(3);
        algorithmChosen = algorithmNames[algorithmNumber];

        try {


            algorithm = new Algorithm(algorithmChosen);
            SecretKey secretKey = algorithm.GenerateKey();
            String encrypted = algorithm.encrypt(message,secretKey);
            //test.setText();


            Log.e("encrypted message", encrypted);
            message = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT)+","+algorithmNumber+","+encrypted;
            Log.e("message", message);
            Log.e("secret key", secretKey.toString());
            Log.e("secretKeyString", Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
            send = rsa.encrypt(message);

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


    }

    public void send(View v){
        if(!ip.getText().toString().equals("")) FinalIP = ip.getText().toString();
        else FinalIP = "192.168.0.100";


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else connected = false;

        if(connected) {

            Socket_AsyncTask sender = new Socket_AsyncTask();

            try {
                sender.execute();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "no host of that ip found", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
        }
        //maybe open a text box asking if you wanna record again
        Intent i =  new Intent(getApplicationContext(),Mode_Pick.class);
        i.putExtra("name",name);
	i.putExtra("password",password);
        startActivity(i);
    }

    public class Socket_AsyncTask extends AsyncTask<String,Void,String> {

        Socket s;
        DataOutputStream dos;

        protected String doInBackground(String...params){

            try{
                //put infinite while loop that generates, encodes and sends every couple of seconds
                Log.e("response", "trying");
                s = new Socket(FinalIP,8000);
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(""+send);
                Log.e("response", "sent");
                //Log.e("decrypted", decrypted);
                dos.close();
                s.close();

            }catch(IOException e ){
                e.printStackTrace();
            }

            return null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(VitalSignsResults.this, Mode_Pick.class);
        i.putExtra("name",name);
        i.putExtra("password",password);
        startActivity(i);
        finish();
    }

}
