package ir.daneh.danehattendance

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast

class MyOnItemSelected(private val context: Context?) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        Toast.makeText(context, "Selected value : $position", Toast.LENGTH_LONG).show()
        //mainActivity.eventSpinnerIndex = position
    }
}