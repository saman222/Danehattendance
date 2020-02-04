package ir.daneh.danehattendance.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.zxing.Result
import ir.daneh.danehattendance.CheckInternetAsyncTask
import ir.daneh.danehattendance.MyAsyncTask
import ir.daneh.danehattendance.MyOnItemSelected
import ir.daneh.danehattendance.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), ResultHandler {
    private var eventId: ArrayList<Int> = arrayListOf(0)
    private var eventName: ArrayList<String> = arrayListOf("")
    private var scannerView: ZXingScannerView? = null
    private var melicode: TextView? = null
    private var spinnerEvent: Spinner? = null
    private val REQUEST_CAMERA = 1
    private lateinit var txtResponse: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        txtResponse = view.txt_response
        spinnerEvent = view.spinnerEvent
        val checkInternet = CheckInternetAsyncTask(view.context)
        val check = checkInternet.execute()
        if (check.get()) {
            getEventsResponse()
            val arrayAdapter = ArrayAdapter(
                context!!, android.R.layout.simple_spinner_dropdown_item, eventName
            )
            spinnerEvent!!.adapter = arrayAdapter
            spinnerEvent!!.onItemSelectedListener = MyOnItemSelected(context)
        }

        if (!checkPermission()) {
            requestPermission()
        }
        scannerView = view.scanner
        melicode = view.melicode
        val requestButton = view.btn_request
        requestButton.setOnClickListener {
            Toast.makeText(context, "Clicked!", Toast.LENGTH_LONG).show()
            getResponse(melicode)
        }




        return view
    }

    override fun onResume() {
        super.onResume()
        if (checkPermission()) {
            if (scannerView == null) {
                scannerView = view?.scanner
            }
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this.activity!!,
            arrayOf(
                android.Manifest.permission.CAMERA
            ),
            REQUEST_CAMERA
        )
    }

    private fun getEventsResponse() {
        val endPoint =
            "https://daneh.ir/index.php?option=com_fabrik&format=raw&task=plugin.userAjax&method=getEvents"
        Log.i("test-app", "get ! $endPoint")
        MyAsyncTask(eventId, eventName, txtResponse).execute(endPoint)
    }

    private fun getResponse(melicode: TextView?) {
        val melicode = melicode?.text
        val endPoint = "https://daneh.ir/index.php?option=com_fabrik" +
                "&format=raw&task=plugin.userAjax&method=getIdFromMelliCode" +
                "&meli_code=" + melicode + "&event_number=" + eventName.indexOf(spinnerEvent?.selectedItem.toString())
        Log.i("test-app", "get ! $endPoint")
        MyAsyncTask(eventId, eventName, txtResponse).execute(endPoint)
    }


    override fun handleResult(rawResult: Result?) {
        val result: String? = rawResult?.text
        val vibrator = context?.getSystemService(
            Context.VIBRATOR_SERVICE
        ) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
        melicode?.text = result
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }
}
