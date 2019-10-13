package com.example.finalyear;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RC4 {
    //create a keygenerator based upon the
    KeyGenerator keygenerator;

    {
        try {
            keygenerator = KeyGenerator.getInstance("Blowfish");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //create a key
    SecretKey secretkey=keygenerator.generateKey();
    //create a cipher based upon Blowfish
    Cipher cipher;

    {
        try {
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE,secretkey);
            //initialise cipher to with secretkey

            //get the text to encrypt
            String inputText = "Hello world";
            //encrypt message
            byte[] encrypted=cipher.doFinal(inputText.getBytes());
            //re-initialise the cipher to be in decrypt mode
            cipher.init(Cipher.DECRYPT_MODE,secretkey);
            //decrypt message
            byte[] decrypted=cipher.doFinal(encrypted);
            //and display the results
            System.out.println("Original String: " + inputText);
            System.out.println("Encrypted: " + new String(encrypted));
            System.out.println("Decrypted: " + new String(decrypted));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
