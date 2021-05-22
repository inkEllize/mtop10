package com.csr1.forreverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)
        mainViewModel.valueLiveData.observe(this, Observer {
            if (it == 1)
                supportFragmentManager.beginTransaction().replace(R.id.container, FirstFragment.newInstance("a", "b")).addToBackStack(null).commit()
            else {
                supportFragmentManager.beginTransaction().replace(R.id.container, SecondFragment.newInstance("a", "b")).addToBackStack(null).commit()
            }
        })
        val btn1 = findViewById<Button>(R.id.btn_1)
        btn1.setOnClickListener { mainViewModel.valueLiveData.value = 1 }
        findViewById<Button>(R.id.button2).setOnClickListener { mainViewModel.valueLiveData.value = 2 }

    }
}