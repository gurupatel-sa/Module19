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
import android.os.PowerManager

class ProximitySensorExample : AppCompatActivity(), SensorEventListener {
    val TAG: String = this.javaClass.getSimpleName()
    val SAMPLING_PERIOD = 2 * 1000 * 1000

    var sensorManager: SensorManager? = null
    var proximitySensor: Sensor? = null
    var field: Int? = null
    var powerManager: PowerManager? = null
    var wakeLock: PowerManager.WakeLock? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity_sensor_example)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        field = PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK
        powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager?.newWakeLock(field!!, getLocalClassName());

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
            proximitySensor, SAMPLING_PERIOD
        );
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        if (wakeLock?.isHeld()!!) {
            wakeLock?.release();
        }
        sensorManager?.unregisterListener(this)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d(TAG, "onAccuracyChanged :")
    }

    @SuppressLint("WakelockTimeout")
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        Log.d(TAG, "onSensorChanged :")
        if (sensorEvent!!.values[0] < proximitySensor?.getMaximumRange()!!) {
            Toast.makeText(this, "In range", Toast.LENGTH_SHORT).show();
            if (!wakeLock?.isHeld()!!) {
                wakeLock?.acquire();
            }
        } else {
            if (wakeLock?.isHeld()!!) {
                wakeLock?.release();
            }
        }
    }
}