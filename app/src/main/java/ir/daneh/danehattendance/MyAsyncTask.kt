package ir.daneh.danehattendance

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MyAsyncTask(
    private val eventId: ArrayList<Int>,
    private val eventName: ArrayList<String>,
    private val txtResponse: TextView
) : AsyncTask<String, String, String>(){
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
//            Log.i("test-app", "get !")
        try {
            val json = JSONArray(values[0])
            val len = json.length()
            for(index in 0 until len){
                Log.i("test-app", "get ! ($index) : " + json.getJSONObject(index))
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
            txtResponse.text = json.getString(0)
        }
        catch (ex:Exception)
        {
            txtResponse.text = ""
        }

    }
}