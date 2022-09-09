package com.example.android.greenflag

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android.greenflag.databinding.ActivityMainBinding


private val LOG_TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun createAccountActivity(view: View) {
        Log.d(LOG_TAG, "Create account clicked!")
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }
}