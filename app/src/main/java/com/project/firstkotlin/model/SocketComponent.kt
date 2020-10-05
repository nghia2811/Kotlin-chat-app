package com.project.firstkotlin.model

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketComponent private constructor() {

//    private val SERVER_URL = "https://first-server-nghia.herokuapp.com/"
    private val SERVER_URL = "http://192.168.43.30:3000/"

    companion object {
        private var INSTANCE: SocketComponent? = null
        val instance: SocketComponent?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = SocketComponent()
                }
                return INSTANCE
            }
    }

    // other instance methods can follow
    var socket: Socket? = null
        get() {
            if (field == null) {
                try {
                    field = IO.socket(SERVER_URL)
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                }
            }
            return field
        }
        private set


}