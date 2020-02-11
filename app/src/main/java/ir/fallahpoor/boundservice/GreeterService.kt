package ir.fallahpoor.boundservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast

class GreeterService : Service() {

    companion object {
        const val MSG_SAY_HELLO = 1
    }

    private lateinit var messenger: Messenger

    override fun onBind(intent: Intent): IBinder? {
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }

    internal class IncomingHandler(
            context: Context,
            private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO ->
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show()
                else -> super.handleMessage(msg)
            }
        }
    }

}