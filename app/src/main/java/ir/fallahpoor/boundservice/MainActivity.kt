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
    private var isBoundToCalculatorService = false
    private var isBoundToGreeterService = false
    private lateinit var calculatorService: CalculatorService
    private val calculatorServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            /*
             * This is called when the connection with the service has been
             * established, giving us the object we can use to interact with
             * the service.
             */
            logMessage("Connected to CalculatorService")
            isBoundToCalculatorService = true
            val binder = iBinder as CalculatorService.LocalBinder
            calculatorService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // This is called when the connection with the service has been disconnected
            logMessage("Disconnected from CalculatorService")
            isBoundToCalculatorService = false
        }
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
        Intent(this, CalculatorService::class.java).also { intent ->
            bindService(intent, calculatorServiceConnection, Context.BIND_AUTO_CREATE)
        }

    override fun onStop() {
        super.onStop()
        if (isBoundToCalculatorService) {
            unbindService(calculatorServiceConnection)
        }
    }

    fun onClick(view: View?) {

        val operand1 = operandOneEditText.text.toString().toInt()
        val operand2 = operandTwoEditText.text.toString().toInt()

        if (isBoundToCalculatorService) {
            resultTextView.text = when (view?.id) {
                R.id.addButton -> calculatorService.add(operand1, operand2).toString()
                R.id.subtractButton -> calculatorService.subtract(operand1, operand2).toString()
                R.id.multiplyButton -> calculatorService.multiply(operand1, operand2).toString()
                R.id.divideButton -> calculatorService.divide(operand1, operand2).toString()
                else -> "Unknown value"
            }
        } else {
            logMessage("Not bound to CalculatorService. This should not happen!")
        }

    }

    private fun logMessage(message: String) {
        Log.d(tag, message)
    }

}