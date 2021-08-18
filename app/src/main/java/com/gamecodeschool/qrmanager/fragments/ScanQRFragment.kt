package com.gamecodeschool.qrmanager.fragments

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.gamecodeschool.qrmanager.MainActivity
import com.gamecodeschool.qrmanager.R
import com.gamecodeschool.qrmanager.qrscanner.QrScanner
import com.gamecodeschool.qrmanager.patterns.observers.IObserver
import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventArgs
import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventDetectedTextArgs
import com.gamecodeschool.qrmanager.websocket.ServerData
import com.gamecodeschool.qrmanager.websocket.websocketclient.WebSocketClientService
import kotlinx.android.synthetic.main.fragment_scanqr.*
import java.net.URI

class ScanQRFragment : Fragment(), IObserver {

    var scanner: QrScanner? = null;
    var webSocketService = WebSocketClientService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanqr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpScanner()
        setUpControls()
    }

    private fun setUpControls() {
        var buttonAnim: Animation =
            AnimationUtils.loadAnimation(context as MainActivity, R.anim.button_anim)
        btnSend.setOnClickListener { v ->
            v.startAnimation(buttonAnim)
            if (scanner!!.detectedValue.length > 0) {
                try {
                    webSocketService.connect(URI(ServerData.getAddress()))
                    if (webSocketService != null) {
                        Log.i("TAG", webSocketService.toString())
                        webSocketService.sendMessage(scanner!!.detectedValue)
                    }
                    webSocketService.disconnect()
                    Toast.makeText(
                        context as MainActivity, "Sent \"" +
                                scanner!!.detectedValue + "\"", Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(context as MainActivity, "Incorrect server address", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnCopy.setOnClickListener { v ->
            v.startAnimation(buttonAnim)
            if (scanner!!.detectedValue.length > 0) {
                val myClipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("QrCode", scanner!!.detectedValue)
                myClipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        /*cameraSurfaceView.setOnClickListener {
            scanner!!.cameraAction()
        }*/
    }



    override fun onResume() {
        super.onResume()
        scanner!!.start()
        Log.d("QrScanner", "Resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d("QrScanner", "Paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("QrScanner", "Stopped")
        scanner!!.stop()
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d("QrScanner", "onDestroed")
        scanner!!.release()
    }


    private fun turnDownScanner() {
        scanner = null
    }

    private fun setUpScanner() {
        if(scanner == null) {
            scanner =
                QrScanner(cameraSurfaceView, getActivity() as MainActivity, context as MainActivity)
            scanner?.add(this)
        }
    }

    override fun update(arg: EventArgs) {
        val text = arg as EventDetectedTextArgs
        if(text.detectedText.isNotEmpty())
        {
            textScannerResult.text = text.detectedText
        }
        else
            textScannerResult.text = "Detecting barcode..."
    }

}