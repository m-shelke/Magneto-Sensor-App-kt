package com.example.mangnotosensorappkt

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var imageView : ImageView
    lateinit var tvDegree : TextView

    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null

    var currentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       imageView  = findViewById(R.id.imgV_compass)
       tvDegree  = findViewById(R.id.tvDegree)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        var degree = Math.round(event?.values?.get(0)!! )

        tvDegree.text = degree.toString() + "Degree"

        var animation = RotateAnimation(currentDegree,(-degree).toFloat(),Animation.RELATIVE_TO_SELF,05f,Animation.RELATIVE_TO_SELF,05f)
        animation.duration = 210
        animation.fillAfter = true

        imageView.startAnimation(animation)
        currentDegree = (-degree).toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

}