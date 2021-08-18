package com.gamecodeschool.qrmanager.qrscanner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class CameraUtil(private val cameraSurfaceView: SurfaceView,
                 private val activity: Activity,
                 private val context: Context,
                 private val processor: Detector.Processor<Barcode>){

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector

    init {
        if(ContextCompat.checkSelfPermission(context ,android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED)
        {
            askForCameraPermision()
        }
        else
        {
            setupControls()
        }
    }

    private fun setupControls()
    {
        detector=BarcodeDetector.Builder(context).build()
        cameraSource=CameraSource.Builder(context,detector).setAutoFocusEnabled(true)
                                                            .setRequestedPreviewSize(600,600).build()
        cameraSurfaceView.holder.addCallback(SurfaceCallback())
        cameraSurfaceView.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                cameraSource.start()
            }
        }
        detector.setProcessor(processor)
    }

    fun stop() {
        cameraSource.stop()
    }

    fun start() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cameraSource.start()
        }
    }

    fun release() {
        cameraSource.release()
    }


    private fun askForCameraPermision()
    {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),requestCodeCameraPermission)
    }

    inner class SurfaceCallback: SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            try{
                if (ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                cameraSource.start(holder)
            }
            catch (exception: Exception) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

        override fun surfaceDestroyed(holder: SurfaceHolder) { }

    }



}