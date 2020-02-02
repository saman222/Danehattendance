package ir.daneh.danehattendance

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.InputStream
import java.net.URL
import org.json.JSONArray
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler



class MainActivity : AppCompatActivity(), ResultHandler {

    var eventId: ArrayList<Int> = arrayListOf(0)
    var eventName: ArrayList<String> = arrayListOf("")
    var scannerView: ZXingScannerView? = null
    var eventSpinnerIndex = 0
    val REQUEST_CAMERA = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runOnUiThread {
            val checkInternet = CheckInternetAsyncTask(this)
            val check = checkInternet.execute()
            if(check.get())
                getEventsResponse()
        }
        //val eventArray = arrayOf("", "Event 1", "Event 2", "Event 3")
        val arrayAdapter = ArrayAdapter(
            this@MainActivity, android.R.layout.simple_spinner_dropdown_item, eventName
        )
        spinnerEvent.adapter = arrayAdapter
        spinnerEvent.onItemSelectedListener = MyOnItemSelected(this@MainActivity)
        if (!checkPermission()) {
            requestPermission()
        }
        scannerView = findViewById(R.id.scanner)
        val requestButton = findViewById<Button>(R.id.btn_request)
        requestButton.setOnClickListener{
            Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_LONG).show()
            getResponse()
        }
    }

    override fun onResume() {
        super.onResume()
        if(checkPermission()){
            if(scannerView == null){
                scannerView = findViewById(R.id.scanner)
                setContentView(R.layout.activity_main)
            }
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()

    }

    override fun handleResult(rawResult: Result?) {
        val result : String? = rawResult?.text
        val vibrator = applicationContext.getSystemService(
            Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
        melicode.text = result
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MainActivity,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA
            ),
            REQUEST_CAMERA
        )
    }

    private fun getResponse(){
        val melicode = melicode.text
        val endPoint = "https://daneh.ir/index.php?option=com_fabrik" +
                "&format=raw&task=plugin.userAjax&method=getIdFromMelliCode" +
                "&meli_code="+melicode + "&event_number=" + eventId[eventSpinnerIndex]
        Log.i("test", "get ! $endPoint")
        MyAsyncTask().execute(endPoint)
    }
    private fun getEventsResponse(){
        val endPoint = "https://daneh.ir/index.php?option=com_fabrik&format=raw&task=plugin.userAjax&method=getEvents"
        Log.i("test", "get ! $endPoint")
        MyAsyncTask().execute(endPoint)
    }
    @SuppressLint("StaticFieldLeak")
    inner class MyAsyncTask: AsyncTask<String, String, String>(){
        override fun doInBackground(vararg params: String?): String {
            try {
                val url = URL(params[0])

                val urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 10000

                val inString = convertStreamToString(urlConnect.inputStream)
                publishProgress(inString)


            }catch (ex:Exception)
            {

            }
            return ""
        }
        private fun convertStreamToString(inputStream: InputStream): String {
            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            var line:String?
            var allStrings = ""
            try {
                do{
                    line = bufferReader.readLine()
                    allStrings += line
                }while (line != null)

            }
            catch (ex:Exception)
            {

            }

            return allStrings
        }


        override fun onProgressUpdate(vararg values: String?) {
//            super.onProgressUpdate(*values)
//            Log.i("test", "get !")
            try {
                val json = JSONArray(values[0])
                val len = json.length()
                for(index in 0 until len){
                    Log.i("test", "get ! ($index) : " + json.getJSONObject(index))
                    try {
                        eventId.add(json.getJSONObject(index).getInt("id"))
                    }
                    catch (ex:Exception)
                    {

                    }
                    try {
                        eventName.add(json.getJSONObject(index).getString("label"))
                    }
                    catch (ex:Exception)
                    {

                    }
                }
                txt_response.text = json.getString(0)
            }
            catch (ex:Exception)
            {
                txt_response.text = ""
            }

        }
    }
}
