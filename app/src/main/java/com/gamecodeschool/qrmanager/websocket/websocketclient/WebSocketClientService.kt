package com.gamecodeschool.qrmanager.websocket.websocketclient

import android.util.Log
import java.lang.Exception
import java.net.URI
import java.util.concurrent.TimeUnit

class WebSocketClientService {
    private val TAG = "WebSocketClientService"
    private lateinit var clientSocketKotlin : WebSocketClientKotlin


    fun connect(serverURI: URI) {
        clientSocketKotlin = WebSocketClientKotlin(serverURI)

        try {
            clientSocketKotlin.connectBlocking(1, TimeUnit.SECONDS)
            if(!clientSocketKotlin.socket!!.isConnected) {
                Log.d(TAG, "connection failed: invalid address")
                throw Exception("Invalid address")
            }
            else
                Log.d(TAG, "connection failed")
            Log.d(TAG, "connected")
        }
        catch(ex: InterruptedException)
        {
            Log.d(TAG, "Exception: " + ex.toString())
            throw Exception(ex.toString())
        }
    }

    fun disconnect() {
        clientSocketKotlin.close()
    }

    fun sendMessage(mes: String) {
        if(clientSocketKotlin.isOpen()) {
            clientSocketKotlin.send(mes)
        }
    }

}