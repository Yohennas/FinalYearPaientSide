package com.example.finalyear;

import android.util.Log;

import java.math.BigInteger;

public class RSAReciever {
    private final static BigInteger one      = new BigInteger("1");
    private final static BigInteger zero      = new BigInteger("0");
    private final static BigInteger two      = new BigInteger("2");

    private BigInteger publicKey; // e
    private BigInteger n;
    private BigInteger z;
    private BigInteger privateKey;

    RSAReciever() {
        BigInteger p = (two.pow(2203)).subtract(one);//new BigInteger("5700734181645378434561188374130529072194886062117");//
        BigInteger q = (two.pow(2281)).subtract(one);//new BigInteger("35894562752016259689151502540913447503526083241413");//
        z = (p.subtract(one)).multiply(q.subtract(one));
        n    = p.multiply(q);
        publicKey = new BigInteger("33445843524692047286771520482406772494816708076993");
        privateKey = publicKey.modInverse(z);

    }


    String decrypt(String encrypted) {
        BigInteger IntEncrypted = new BigInteger(encrypted);
        Log.e("encrypted", ""+IntEncrypted);
        BigInteger IntDecrypted = IntEncrypted.modPow(privateKey, n);//new BigInteger("1942905859914315532863108578401476704989860287997340468598002326953119719280123920996283974995026985531714758164430090");//
        Log.e("decrypted", ""+IntDecrypted);
        byte[] bytes = IntDecrypted.toByteArray();
        Log.e("byte array", ""+bytes);
        String decrypted = new String(bytes);
        return decrypted;
    }
}
