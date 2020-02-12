package ir.daneh.danehattendance.asynctasks

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import java.net.InetSocketAddress
import java.io.IOException
import java.net.Socket
import kotlin.system.exitProcess

class CheckInternetAsyncTask(private val context: Context) : AsyncTask<Void, Boolean, Boolean>() {
    override fun doInBackground(vararg p0: Void?): Boolean {
        return try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            publishProgress(true)
            return true
        } catch (e: IOException) {
            publishProgress(false)
            false
        }
    }

    override fun onProgressUpdate(vararg values: Boolean?) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Internet Connection")
        alertDialogBuilder.setCancelable(false)
        if (!values[0]!!) {
            alertDialogBuilder.setMessage("Not Connected!")
            alertDialogBuilder.setPositiveButton(context.resources.getString(android.R.string.ok)) { _, _ ->
                exitProcess(-1)
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


    }
}