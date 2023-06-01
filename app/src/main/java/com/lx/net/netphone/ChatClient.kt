package com.lx.net.netphone

import com.lx.net.common.utils.LogCompat.logE
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.lang.Exception

object ChatClient {

    private var mSocket: Socket? = null



    private val onConnectEvent = Emitter.Listener {
        it.forEach { listen ->
            ("it == $listen").logE()
        }

        ("it ==  onConnectEvent").logE()
    }

    fun initClient(){
        if (mSocket == null){
            try {
                mSocket = IO.socket("ws://192.168.10.45:9527/chat");
            } catch (e: Exception){
                ("error  ${e.message}").logE()
            }

        }
        mSocket?.on(Socket.EVENT_CONNECT, onConnectEvent);
        mSocket?.connect()
    }
}