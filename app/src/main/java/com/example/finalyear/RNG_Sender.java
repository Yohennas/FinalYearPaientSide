package com.example.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RNG_Sender extends AppCompatActivity {


    RSASender rsa;
    //RSAReciever rsaR;
    Algorithm algorithm;
    EditText ip;
    Random rand;
    TextView BloodPressure, HeartRate, Respiration, Temperature, BloodOxygen, test;
    String bloodPressure, heartRate, bloodOxygen, respiration, temperature, algorithmChosen, encryptedMessage;
    String message, name, password;
    String[] algorithmNames;
    int algorithmNumber;
    BigInteger send;
    String FinalIP;
    boolean connected;
    DataBase dataBase;
    Pateint pateint;
    ScheduledExecutorService scheduledExecutorService;
    ScheduledExecutorService scheduledExecutorService2;
    Cache cache;
    //String decrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rng__sender);

        connected = false;

        ip = findViewById(R.id.ipValue);
        BloodPressure = findViewById(R.id.bloodValue);
        HeartRate = findViewById(R.id.heartValue);
        Respiration = findViewById(R.id.respirationValue);
        Temperature = findViewById(R.id.temperatureValue);
        BloodOxygen = findViewById(R.id.bloodOxyValue);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        password = bundle.getString("password");
        cache = new Cache(this);
        final int version = Integer.parseInt(cache.getStringProperty("versionNumber"));
        dataBase = new DataBase(getApplicationContext(),name, version, password);

        rand = new Random();



        algorithmNames = new String[]{"Blowfish", "AES", "RC4"};
        //initialise RSA class
        rsa = new RSASender();
       // rsaR = new RSAReciever();

        //randomly choose algorithm
        algorithmNumber = rand.nextInt(3);
        algorithmChosen = algorithmNames[algorithmNumber];

        //random generation of simulation values
        //regenerate and send every 2 seconds
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                RNG_Sender.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bloodPressure = Integer.toString(rand.nextInt(100));
                        heartRate = Integer.toString(rand.nextInt(100));
                        bloodOxygen = Integer.toString(rand.nextInt(100));
                        respiration = Integer.toString(rand.nextInt(100));
                        temperature = Integer.toString(rand.nextInt(100));

                        long start = System.currentTimeMillis();

                        BloodPressure.setText(bloodPressure);
                        HeartRate.setText(heartRate);
                        BloodOxygen.setText(bloodOxygen);
                        Respiration.setText(respiration);
                        Temperature.setText(temperature);

                        //maybe save as encrypted but send as normal
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


			
			//maybe weave encryption into the patient class
                        pateint = new Pateint(name,password,age,height,weight,sex,bloodPressure,respiration,bloodOxygen,heartRate);

                        dataBase.addInstance(pateint);

                        //instead of sending just as toString rather send indiviudally
                        //construction of message to be sent
			//toString decrypts data (maybe)
                        message = bloodPressure+" "+heartRate+" "+bloodOxygen+" "+respiration+" "+pateint.toString()+" "+version+" "+temperature;

                        //initialise algorithm class and encrypt in both RSA and symmetric
                        try {


                            algorithm = new Algorithm(algorithmChosen);
                            SecretKey secretKey = algorithm.GenerateKey();

                            //long startEncrypt = System.currentTimeMillis();
                            long startEncrypt = System.nanoTime();
                            String encrypted = algorithm.encrypt(message,secretKey);
                            //long finishEncrypt = System.currentTimeMillis();
                            long finishEncrypt = System.nanoTime();
                            long totalEncrypt = finishEncrypt - startEncrypt;
                            Log.e("totalEncrypt (Blowfish)",totalEncrypt+"") ;




                            message = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT)+","+algorithmNumber+","+encrypted;


                            //long startRSA = System.currentTimeMillis();
                            long startRSA = System.nanoTime();
                            send = rsa.encrypt(message);
                            //long finsihRSA = System.currentTimeMillis();
                            long finsihRSA = System.nanoTime();
                            long totalRSA = finsihRSA - startRSA;
                            Log.e("totalRSA(2203 & 2281)",totalRSA+"") ;

                            long finish = System.currentTimeMillis();

                            long total = finish - start;
                            Log.e("total",total+"") ;



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
                });
            }
        },0,2, TimeUnit.SECONDS);

    }

    public void send(View v){
        if(!ip.getText().toString().equals("")) FinalIP = ip.getText().toString();
        else FinalIP = "192.168.0.100";
        scheduledExecutorService2 = Executors.newScheduledThreadPool(1);
        scheduledExecutorService2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                RNG_Sender.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                    }
                });
            }
        },0,2, TimeUnit.SECONDS);

    }

    public void stop (View v){
        if (connected) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService2.shutdown();
        }
        else scheduledExecutorService.shutdown();

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
                s = new Socket(FinalIP,5000);
                dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(""+send);
                long sent = System.currentTimeMillis();
                Log.e("Sent",sent+"") ;

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
        Intent i = new Intent(RNG_Sender.this, Mode_Pick.class);
        i.putExtra("name",name);
        i.putExtra("password",password);
        startActivity(i);
        finish();
    }
}
