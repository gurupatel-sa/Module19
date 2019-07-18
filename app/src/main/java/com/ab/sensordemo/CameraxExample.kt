package com.ab.sensordemo

import android.annotation.SuppressLint
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import kotlinx.android.synthetic.main.activity_camerax_example.*
import java.io.File

class CameraxExample : AppCompatActivity() {
    private val TAG: String = this.javaClass.getSimpleName()

    private lateinit var imageCapture: ImageCapture
    private lateinit var lensFacing: CameraX.LensFacing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax_example)
        Log.d(TAG, "onCreate")
        camera_view.post {
            initCamera()
        }

        camera_view.addOnLayoutChangeListener { view, i, j, k, l, m, n, o, p ->
            Log.d(TAG, "addOnLAyoutChage")
            updateTransform()
        }

        captureImage.setOnClickListener(View.OnClickListener {
            var fileLoc = File(cacheDir, "image.jpg")
            imageCapture.takePicture(fileLoc, object : ImageCapture.OnImageSavedListener {
                override fun onImageSaved(file: File) {
                    Log.d(TAG, "Inmage Saved " + file.absolutePath)
                    Toast.makeText(this@CameraxExample, "Image saved in cache ${file.absolutePath}", Toast.LENGTH_SHORT)
                        .show();
                }

                override fun onError(useCaseError: ImageCapture.UseCaseError, message: String, cause: Throwable?) {
                    Log.d(TAG, "onError : Message ${message}")

                }
            })
        })
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

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
    }

    private fun updateTransform() {
        Log.d(TAG, "updateTransform")

        val matrix = Matrix()

        val centerX = camera_view.width / 2f
        val centerY = camera_view.height / 2f

        val rotationDegrees = when (camera_view.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        camera_view.setTransform(matrix)
    }

    @SuppressLint("NewApi")
    private fun initCamera() {
        Log.d(TAG, "initCamera")
        lensFacing = CameraX.LensFacing.BACK

        //set preview mode
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(640, 640))
            setLensFacing(lensFacing)
        }
        val preview = Preview(previewConfig.build())
        preview.setOnPreviewOutputUpdateListener {

            Log.d(TAG, "setOnPreviewListner")
            val parent = camera_view.parent as ViewGroup
            parent.removeView(camera_view)
            parent.addView(camera_view, 1)

            camera_view.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        //set image capture mode
        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }
        imageCapture = ImageCapture(imageCaptureConfig.build())

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }
}
