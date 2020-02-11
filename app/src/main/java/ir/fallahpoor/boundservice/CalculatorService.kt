package ir.fallahpoor.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class CalculatorService : Service() {

    private val binder: LocalBinder = LocalBinder()

    /* When a component calls bindService() this method is called to return the binder to calling
     * component.
     * The system caches the IBinder service communication channel. In other words, the system
     * calls the service's onBind() method to generate the IBinder only when the first client
     * binds. The system then delivers that same IBinder to all additional clients that bind to
     * that same service, without calling onBind() again.
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
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of BoundService so clients can call public methods
        fun getService(): CalculatorService = this@CalculatorService
    }

}