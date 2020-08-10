package com.nestudent.android.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.nestudent.android.myapplication.api.Repository
import com.nestudent.android.myapplication.api.model.RegisterData

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Repository.instance.registerUser(
            RegisterData(
                login = "assgd",
                password = "asdgasgd",
                firstName = "asdga",
                lastName = "asdgsa"
            )
        ).observe(this, Observer {
            Log.d(TAG, "$it")
        })
    }
}