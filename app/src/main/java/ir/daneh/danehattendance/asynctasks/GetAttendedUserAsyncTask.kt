package ir.daneh.danehattendance.asynctasks

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GetAttendedUserAsyncTask : AsyncTask<Void, String, ArrayList<String>>() {
    private lateinit var jsonOutPut : JSONArray

    override fun doInBackground(vararg params: Void?): ArrayList<String> {
        var output = arrayListOf<String>()
        try {
            Log.i("test-app", "In try")
            val url =
                URL("https://daneh.ir/index.php?option=com_fabrik&format=raw&task=plugin.userAjax&method=getAttendedUser")

            val urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connectTimeout = 10000

            val inString = convertStreamToString(urlConnect.inputStream)
//            Log.i("test-app", "In try inString = $inString")
            publishProgress(inString)
            jsonOutPut = JSONArray(inString)
            val len = jsonOutPut.length()
            Log.i("test-app", "len json = $len")
            for (index in 0 until len) {
                var name = ""
                name += jsonOutPut.getJSONObject(index).get("name")
                name += " " + jsonOutPut.getJSONObject(index).get("family")
                output.add(name)
            }

        } catch (ex: Exception) {
            Log.i("test-app", "In catch")
            ex.printStackTrace()
        }
        return output
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        var allStrings = ""
        try {
            do {
                line = bufferReader.readLine()
                allStrings += line
            } while (line != null)

        } catch (ex: Exception) {

        }

        return allStrings
    }

}