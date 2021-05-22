package com.grooming.mtop10;

import android.util.Log;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkClass() {
        MyClass myClass = new MyClass(1, 3.14f);
        String psw = "password";
        String res = myClass.someMethod2(psw);
        System.out.println(res);
    }

    @Test
    public void rndExample() {

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        int a = random.nextInt();


//        System.out.println("rnd alg: " + random.getAlgorithm() + "a = "+ a);
        System.out.println("bytes:" + new String(bytes));

        Random simpleRandom = new Random();
        simpleRandom.setSeed(89999222);
        simpleRandom.nextBytes(bytes);
        System.out.println("simple random:" + new String(bytes));


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            try {

                SecureRandom superRandom = SecureRandom.getInstanceStrong();

                System.out.println("super rnd alg:" + superRandom.getAlgorithm());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println("error su[er rnd");
            }
        }

    }

    @Test
    public void rndCycle() {

        byte[] bytesSecure = new byte[20];
        byte[] bytesSimple = new byte[20];

        for (int i = 0; i < 10; ++i) {
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(1234567);

            Random simpleRandom = new Random();
            simpleRandom.setSeed(89999222);

            secureRandom.nextBytes(bytesSecure);
            simpleRandom.nextBytes(bytesSimple);
            System.out.println("simple:" + Base64.getEncoder().encodeToString(bytesSimple) +
                    " | secure:" + Base64.getEncoder().encodeToString(bytesSecure));
        }


    }


    @Test
    public void encrypt() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        String password = "password";
        String message = "My, message to mr. President. Здравствуйте";
        int iterationCount = 1000;
        int keyLength = 256;
        int saltLength = keyLength / 8;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt,
                iterationCount, keyLength);
        //жаль, но PBKDF2WithHmacSHA256 у нас нет до 26 апи :(
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256");
//                .getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();


        //ключ для алгоритма AES
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        random.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        byte[] ciphertext = cipher.doFinal(message.getBytes("UTF-8"));
        System.out.println("encrypted: " + Base64.getEncoder().encodeToString(ciphertext));
        System.out.println("encrypted: " + new String(ciphertext));
//        ciphertext[1] = 'b';
        byte[] shifted = Arrays.copyOf(ciphertext, ciphertext.length - 1);

        byte[] shifted2 = new byte[ciphertext.length];
        shifted2[0] = 0;
        for (int i=1; i < ciphertext.length;++i){
            shifted2[i] = ciphertext[i-1];
        }

        //расшифрование, нам нужен тот же вектор инициализации
        Cipher deChCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        deChCipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] plain = deChCipher.doFinal(shifted2);
        System.out.println("decrypted:" + new String(plain));

    }


    @Test
    public void encryptGCM() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        String password = "password";
        String message = "My, message to mr. President. Здравствуйте";
        int iterationCount = 1000;
        int keyLength = 256;
        int saltLength = keyLength / 8;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt,
                iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        //ключ для алгоритма AES
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[cipher.getBlockSize()];
        random.nextBytes(iv);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(96, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        byte[] cipheredText = cipher.doFinal(message.getBytes("UTF-8"));
        System.out.println("encrypted: " + Base64.getEncoder().encodeToString(cipheredText));
        System.out.println("encrypted: " + new String(cipheredText));
        cipheredText[1] = 'b';
        //расшифрование - указываем длину тэга аутентификации и вектор
        Cipher deChCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpecD = new GCMParameterSpec(96, iv);
        deChCipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpecD);
        byte[] plain = deChCipher.doFinal(cipheredText);
        System.out.println("decrypted: " + new String(plain));

    }
}