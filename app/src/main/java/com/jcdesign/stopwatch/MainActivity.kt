package com.jcdesign.stopwatch

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class MainActivity : AppCompatActivity() {


    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    private lateinit var tvTimer: TextView

    private var seconds = 0
    private var isRunning = false
    private var wasRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds")
            isRunning = savedInstanceState.getBoolean("isStart")
            wasRunning  = savedInstanceState.getBoolean("wasRunning")
        }


        runTimer()

    }

//    override fun onStop() {
//        super.onStop()
//        wasRunning = isRunning
//        isRunning = false
//    }
//
//    override fun onStart() {
//        super.onStart()
//        isRunning = wasRunning
//    }

    override fun onPause() {
        super.onPause()
        wasRunning = isRunning
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = wasRunning
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("isStart", isRunning)
        outState.putBoolean("wasRunning", wasRunning)
    }

    private fun init(){
        btnStart = findViewById(R.id.buttonStart)
        btnStop = findViewById(R.id.buttonStop)
        btnReset = findViewById(R.id.buttonReset)
        tvTimer = findViewById(R.id.textViewTimer)

        btnStart.setOnClickListener { isRunning = true }
        btnStop.setOnClickListener { isRunning = false }
        btnReset.setOnClickListener {
            isRunning = false
            seconds = 0
        }

    }


    private fun runTimer() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
                tvTimer.setText(time)
                if (isRunning) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}