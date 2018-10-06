package ir.fallahpoor.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BoundService : Service() {

    // This binder is returned to clients
    private val binder: LocalBinder = LocalBinder()

    /* When a component calls bindService() this method is called to return the binder to calling
     * component.
     */
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    // Methods for clients
    fun add(a: Int, b: Int) = a + b

    fun subtract(a: Int, b: Int) = a - b

    fun multiply(a: Int, b: Int) = a * b

    fun divide(a: Int, b: Int) = a / b

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of BoundService so clients can call public methods
        fun getService(): BoundService = this@BoundService
    }

}