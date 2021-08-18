package com.gamecodeschool.qrmanager.websocket.websocketclient

import android.util.Log
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.client.WebSocketClient;
import java.lang.Exception
import java.net.URI

class WebSocketClientKotlin(serverUri: URI?) : WebSocketClient(serverUri) {
    private val TAG = "WebSocketClient"

    override fun onOpen(p0: ServerHandshake?) {
        Log.d(TAG, "onOpen")
    }

    override fun onMessage(p0: String?) {
        Log.d(TAG, "onMessage")
    }

    override fun onClose(p0: Int, p1: String?, p2: Boolean) {
        Log.d(TAG, "onClose")
    }

    override fun onError(p0: Exception?) {
        Log.d(TAG, "Error: " + p0.toString())
    }
}