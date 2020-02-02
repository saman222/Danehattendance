package ir.daneh.danehattendance

import android.view.View
import android.widget.AdapterView
import android.widget.Toast

class MyOnItemSelected(m : MainActivity) : AdapterView.OnItemSelectedListener {
    private var mainActivity = m
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        Toast.makeText(mainActivity, "Selected value : $position", Toast.LENGTH_LONG).show()
        mainActivity.eventSpinnerIndex = position
    }
}