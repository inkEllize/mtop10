package com.csr1.jetsecexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.MasterKeys;

import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        try {
//            getMasterKey();
            generateKey();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            textView.append(e.getLocalizedMessage());
        }
//        } catch (IOException e) {
//            e.printStackTrace();
//            textView.append(e.getLocalizedMessage());
//        }
    }

    void getMasterKey() throws GeneralSecurityException, IOException {


        String simpleMasterkeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Key key = keyStore.getKey(simpleMasterkeyAlias,null);
        Cipher cipher = Cipher.getInstance("AES/GCM/"+KeyProperties.ENCRYPTION_PADDING_NONE);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal("hello from android key store".getBytes());


        textView.append("\nsimple master key alias AES256_GCM:" + simpleMasterkeyAlias);
        textView.append("\nsimple key :" + key.getFormat());
        textView.append("\nsimple encrypt:" + new String(encrypted));
        //********************************************************


        KeyGenParameterSpec keySpec =
                new KeyGenParameterSpec.Builder("master", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
//                        .setAlgorithmParameterSpec()
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(256)
//                        .setUserAuthenticationRequired(true)
//                        .setUserAuthenticationParameters(15, KeyProperties.AUTH_DEVICE_CREDENTIAL)
                        .setUserAuthenticationValidityDurationSeconds(15)
//                        .setUnlockedDeviceRequired(true)
//                        .setIsStrongBoxBacked(true)
                        .build();


        String customMasterKeyAlias = MasterKeys.getOrCreate(keySpec);
        textView.append("\ncustom MKA:" + customMasterKeyAlias);
        KeyStore keyStore2 = KeyStore.getInstance("AndroidKeyStore");
        keyStore2.load(null);
        KeyStore.Entry entry = keyStore2.getEntry(customMasterKeyAlias,null);
//        keyStore.


    }

    void generateKey() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyStoreException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, IOException {
        String keyAlias = "MyAlias";
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(keyAlias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setRandomizedEncryptionRequired(true)
                .build();
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore");
        keyGenerator.init(keyGenParameterSpec);

        SecretKey secretKey = keyGenerator.generateKey();



        textView.append("\n===================================================");
        textView.append("\nsecret key" + secretKey.toString());

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
//        Key key = keyStore.getKey(keyAlias,null);
        Cipher cipher = Cipher.getInstance("AES/CBC/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] encrypted = cipher.doFinal("encryt hello world".getBytes());
        textView.append("\nencrypted"+ new String(encrypted));


    }

}