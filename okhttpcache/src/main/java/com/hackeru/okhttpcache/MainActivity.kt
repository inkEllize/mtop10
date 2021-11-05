package com.hackeru.okhttpcache

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var client: OkHttpClient? = null
    lateinit var tv_log:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_log = findViewById(R.id.tv_answer)
    }
    fun makeRequest(url:String): Request {
        return Request.Builder()
                .url(url)
                .build()
    }


    fun goToURl(view: View) {
        if (client == null) {
            client = OkHttpClient.Builder()
                    .cache(
                            Cache(directory = File(application.cacheDir, "interesting_data"), maxSize = 1024L * 1024 * 10)
                    ).build()
        }

        val url = findViewById<EditText>(R.id.ed_url).text.toString()
        Log.d("GO","go to url $url")
        client!!.newCall(makeRequest(url)).enqueue( object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GO",e.toString())
                tv_log.append("${e.message}\n")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("GO","OK ${response.code}")
                tv_log.append("answer: ${response.code}, ${response.headers["date"]}\n")
            }
        })
    }
}