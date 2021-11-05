package com.hackeru.somecrypto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyInfo
import android.view.TextureView
import android.view.View
import android.widget.TextView
import java.security.KeyStore
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory

class MainActivity : AppCompatActivity() {
    lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null,null)
            val key = keyStore.getKey("key1","abcdefg".toCharArray())
//            val keyInfo = SecretKeyFactory.getInstance(key.algorithm).getKeySpec(key as SecretKey?,KeyInfo::class.java)
            tv.text = "type: " + keyStore.type+"\n"
            tv.append("provider: "+keyStore.provider+"\n")
            tv.append("aliases: "+keyStore.aliases().toList()+"\n")
            tv.append("size: "+keyStore.size()+"\n")
            tv.append("str: "+keyStore.toString()+"\n")
            tv.append("-----------------------------------------------------------------\n")
//            val mainKey = MasterKey.Builder(applicationContext)
//                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//                    .build()


        }
    }

    fun onClick(view: View) {

    }
}