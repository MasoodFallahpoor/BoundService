package ir.fallahpoor.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private var isBoundToService = false
    private var boundService: BoundService? = null
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(tag, "Disconnected from service")
            isBoundToService = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(tag, "Connected to service")
            isBoundToService = true
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        intent = Intent(this, BoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBoundToService) {
            unbindService(serviceConnection)
        }
    }

    fun onClick(view: View?) {

        val operand1 = operandOneEditText.text.toString().toInt()
        val operand2 = operandTwoEditText.text.toString().toInt()

        if (isBoundToService) {
            resultTextView.text = when (view?.id) {
                R.id.addButton -> boundService?.add(operand1, operand2).toString()
                R.id.subtractButton -> boundService?.subtract(operand1, operand2).toString()
                R.id.multiplyButton -> boundService?.multiply(operand1, operand2).toString()
                R.id.divideButton -> boundService?.divide(operand1, operand2).toString()
                else -> "Unknown value"
            }
        } else {
            Log.d(tag, "Not bound to service. This should never happen!")
        }

    }

}
