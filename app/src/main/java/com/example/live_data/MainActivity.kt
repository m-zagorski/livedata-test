package com.example.live_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_live_data.setOnClickListener {
            startActivity(LiveDataActivity.newIntent(this))
        }

        main_rx_java.setOnClickListener {

        }
    }
}
