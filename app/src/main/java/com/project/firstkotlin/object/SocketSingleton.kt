package com.project.firstkotlin.`object`

import io.socket.client.IO
import io.socket.client.Socket

object SocketSingleton {
    private var socket = IO.socket("http://192.168.1.185:3000/")
    fun getSocket(): Socket {
        return socket
    }
}