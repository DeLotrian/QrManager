package com.gamecodeschool.qrmanager.websocket

class ServerData {
    companion object{
        var ipAddress: String? = ""
        var port: String? = ""

        fun getAddress(): String {
            return "ws://" + ipAddress + ":" + port
        }
    }
}