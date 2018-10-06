package ir.fallahpoor.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

        val operand1EditText = findViewById<EditText>(R.id.operand_one_edit_text)
        val operand2EditText = findViewById<EditText>(R.id.operand_two_edit_text)
        val resultEditText = findViewById<TextView>(R.id.result_text_view)

        val operand1 = operand1EditText.text.toString().toInt()
        val operand2 = operand2EditText.text.toString().toInt()

        if (isBoundToService) {
            resultEditText.text = when (view?.id) {
                R.id.add_button -> boundService?.add(operand1, operand2).toString()
                R.id.subtract_button -> boundService?.subtract(operand1, operand2).toString()
                R.id.multiply_button -> boundService?.multiply(operand1, operand2).toString()
                R.id.divide_button -> boundService?.divide(operand1, operand2).toString()
                else -> "Unknown value"
            }
        } else {
            Log.d(tag, "Not bound to service. This should never happen!")
        }

    }

}
