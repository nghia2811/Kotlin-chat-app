package com.project.firstkotlin.entity

import io.socket.client.IO
import io.socket.client.Socket

object SocketSingleton {
    private val socket = IO.socket("https://first-server-nghia.herokuapp.com/")
    fun getSocket(): Socket {
        return socket
    }
}