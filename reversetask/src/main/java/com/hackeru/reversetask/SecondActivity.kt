package com.hackeru.reversetask

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val ll = findViewById<LinearLayout>(R.id.ll_second)
        val btn = findViewById<Button>(R.id.button_go_3)
        btn.setOnClickListener {
            if (check()) {
                startActivity(Intent(this, ThirdActivity::class.java))
            }
        }
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        ll.addView(adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun check(): Boolean {
        val sig = packageManager.hasSigningCertificate(packageName, hexToByte(CRT.replace(":", "")), PackageManager.CERT_INPUT_SHA256)
        val s: String = hashString(CRT)
        val ss: String = HASSDDAD
        val two = s.equals(ss)
        return sig;
    }

    fun hexToByte(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}