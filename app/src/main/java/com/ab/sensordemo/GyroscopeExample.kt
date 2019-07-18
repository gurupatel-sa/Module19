package com.ab.sensordemo

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast

class GyroscopeExample : AppCompatActivity(), SensorEventListener {
    private val TAG: String = this.javaClass.getSimpleName()

    private var sensorManager: SensorManager? = null
    private var gyroscopeSensor: Sensor? = null
    private val SAMPLING_PERIOD = 2 * 1000 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gyroscope_example)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscopeSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

        sensorManager?.registerListener(
            this,
            gyroscopeSensor, SAMPLING_PERIOD
        );
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d(TAG, "onAccuracyChanged :")
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        Log.d(TAG, "onSensorChanged :")
        if(sensorEvent?.values!![2] > 0.5f) { //
            Toast.makeText(this, "Left Direction", Toast.LENGTH_SHORT).show();
        } else if(sensorEvent.values[2] < -0.5f) { // clockwise
            Toast.makeText(this, "Right Direction", Toast.LENGTH_SHORT).show();
        }
    }
}