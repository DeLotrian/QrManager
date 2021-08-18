package com.gamecodeschool.qrmanager.qrgenerator

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrGenerator {
    companion object {
        @JvmStatic
        fun encodeText(text: String?): Bitmap? {
            var bitmap: Bitmap? = null
            val multiFormatWriter = MultiFormatWriter()
            try {
                val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300)
                val barcodeEncoder = BarcodeEncoder()
                bitmap = barcodeEncoder.createBitmap(bitMatrix)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
            return bitmap
        }
    }
}