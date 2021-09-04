package com.hackeru.reversetask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.security.MessageDigest

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        val btn = findViewById<Button>(R.id.button_ok)
        Log.d("first", "onCreate: "+ hashString(CRT))
        btn.setOnClickListener { startActivity(Intent(this,SecondActivity::class.java)) }
        val adview = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adview.loadAd(adRequest)
    }

    fun go(view: View) {}
}