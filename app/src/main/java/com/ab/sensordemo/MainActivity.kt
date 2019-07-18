package com.ab.sensordemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LifecycleOwner {
    private val TAG: String = this.javaClass.getSimpleName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchCameraExample.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,CameraExample::class.java)
            startActivity(intent)
        })

        launchCameraxExample.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,CameraxExample::class.java)
            startActivity(intent)
        })

        gyroscopeExample.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,GyroscopeExample::class.java)
            startActivity(intent)
        })

        proxmityExample.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,ProximitySensorExample::class.java)
            startActivity(intent)
        })

        lightExample.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,LightSensorExample::class.java)
            startActivity(intent)
        })
    }
}
