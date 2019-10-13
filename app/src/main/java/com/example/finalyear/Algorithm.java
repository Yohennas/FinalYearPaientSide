package com.example.finalyear;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Algorithm {
    String Algorithm;
    Cipher cipher;

    Algorithm(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        Algorithm = algorithm;
        cipher = Cipher.getInstance(Algorithm);
    }

    public SecretKey GenerateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygenerator = KeyGenerator.getInstance(Algorithm);
        SecretKey secretkey = keygenerator.generateKey();
        return  secretkey;
    }

    /*public SecretKey GenerateFixedKey(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] MDkey = md.digest();
        SecretKeySpec key = new SecretKeySpec(MDkey, "AES");
        return  key;
    }*/


    public String encrypt(String inputText, SecretKey secretKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(inputText.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);


    }

    public String decrypt(String encrypted, SecretKey secretKey) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] ByteDecrypt = Base64.decode(encrypted,Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(ByteDecrypt);
        return new String(decrypted);
    }
}
