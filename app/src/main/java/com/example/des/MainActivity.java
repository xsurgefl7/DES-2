package com.example.des;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;



import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText normaltext;
    EditText keytext;
    EditText ciphertext;

    Button visual;
    Button encrypt;
    Button decrypt;


    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        c = MainActivity.this;

        normaltext = findViewById(R.id.normaltext);
        keytext = findViewById(R.id.key);
        ciphertext = findViewById(R.id.ciphertext);

        encrypt = findViewById(R.id.encrypt);
        decrypt = findViewById(R.id.decrypt);
        visual = findViewById(R.id.visual);

        visual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MainActivity.this, Visual.class);
                startActivity(startIntent);
            }
        });

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ciphertext.setText(encrypt(normaltext.getText().toString(),c));

                }

        });



        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    normaltext.setText(decrypt(ciphertext.getText().toString(),c));
                }

        });

    }

    public static void Loger (String m){
        Log.e("Tag",m);
    }

    public static void DialogMaker (Context c,String title,String mes){
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setCancelable(true);
        alert.setTitle(title);
        alert.setMessage(mes);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }




    public String decrypt(String value,Context c) {


        String coded;
        if(value.startsWith("code==")){
            coded = value.substring(6,value.length()).trim();
        }else{
            coded = value.trim();
        }

        String result = null;

        try {
            // Decoding base64
            byte[] bytesDecoded = Base64.decode(coded.getBytes("UTF-8"),Base64.DEFAULT);

            SecretKeySpec key = new SecretKeySpec(keytext.getText().toString().getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Decrypt the text
            byte[] textDecrypted = cipher.doFinal(bytesDecoded);

            result = new String(textDecrypted);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }
        catch (Exception e) {
            e.printStackTrace();
            DialogMaker(c,"Decrypt Error","Error:" + "\n" + e.getMessage());
            return "Decrypt Error";
        }

        Loger("Decrypt Finished ...");
        return result;
    }


    public String encrypt(String value,Context c) {

        Loger("Encrypt Started ...");

        String crypted = "";

        try {

            byte[] cleartext = value.getBytes("UTF-8");

            SecretKeySpec key = new SecretKeySpec(keytext.getText().toString().getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.ENCRYPT_MODE, key);

            crypted = Base64.encodeToString(cipher.doFinal(cleartext),Base64.DEFAULT);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }
        catch (Exception e) {
            e.printStackTrace();
            DialogMaker(c,"Encrypt Error","Error" + "\n" + e.getMessage());
            return "Encrypt Error";
        }

        Loger("Encrypt Finished ...");

        return crypted;
    }
}