package com.gamecodeschool.qrmanager.qrscanner

import android.app.Activity
import android.content.Context
import android.view.SurfaceView
import com.gamecodeschool.qrmanager.patterns.observers.IObservable
import com.gamecodeschool.qrmanager.patterns.observers.IObserver
import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventArgs
import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventDetectedTextArgs

class QrScanner(private val cameraSurfaceView: SurfaceView,
                private val activity: Activity,
                private val context: Context) : IObserver, IObservable {

    lateinit var camera : CameraUtil
    override val observers: ArrayList<IObserver> = ArrayList()
    var detectedValue: String = ""


    init {
        val processor = BarcodeProcessor()
        processor.add(this)
        camera = CameraUtil(cameraSurfaceView, activity, context, processor)

    }



    override fun update(arg: EventArgs) {
        val textArg = (arg as EventDetectedTextArgs).detectedText
        if(textArg != detectedValue)
        {
            sendUpdateEvent(EventDetectedTextArgs("Update TextView", textArg))
            detectedValue = textArg
        }
    }


    fun stop() {
        camera.stop()
    }

    fun start() {
        camera.start()
    }

    fun release() {
        camera.release()
    }
}