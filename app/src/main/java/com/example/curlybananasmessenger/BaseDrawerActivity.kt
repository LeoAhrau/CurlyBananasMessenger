package com.example.curlybananasmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BaseDrawerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_drawer)
    }
}