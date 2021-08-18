package com.gamecodeschool.qrmanager.qrscanner

import android.util.Log
import android.util.SparseArray
import androidx.core.util.isNotEmpty
import com.gamecodeschool.qrmanager.patterns.observers.IObservable
import com.gamecodeschool.qrmanager.patterns.observers.IObserver
import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventDetectedTextArgs
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode

class BarcodeProcessor(): Detector.Processor<Barcode>, IObservable {
    var detectedText = "";
    override val observers: ArrayList<IObserver> = ArrayList()

    override fun release() { }

    override fun receiveDetections(detections: Detector.Detections<Barcode>) {
        try {
            if (detections.detectedItems.isNotEmpty()) {
                val qrCodes: SparseArray<Barcode> = detections.detectedItems
                val code = qrCodes.valueAt(0)
                sendUpdateEvent(EventDetectedTextArgs( "Barcode detected",code.displayValue))
                detectedText = code.displayValue
            } else {
                sendUpdateEvent(EventDetectedTextArgs( "Barcode is no detected",""))
                detectedText = ""
            }
        }
        catch(ex: java.lang.Exception)
        {
            Log.d("Error", ex.toString())
        }
    }
}