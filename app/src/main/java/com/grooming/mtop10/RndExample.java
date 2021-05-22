package com.grooming.mtop10;

import android.os.Build;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RndExample {



    void example(){
        SecureRandom random = new SecureRandom();

        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        int a = random.nextInt();


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            try {

                SecureRandom superRandom = SecureRandom.getInstanceStrong();

                Log.d("rnd","" + superRandom.nextInt());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
