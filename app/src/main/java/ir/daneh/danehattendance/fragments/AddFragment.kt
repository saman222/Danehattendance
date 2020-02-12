package ir.daneh.danehattendance.fragments


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ir.daneh.danehattendance.R
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.android.synthetic.main.fragment_add.view.*

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add, container, false)
        val view: View = inflater.inflate(R.layout.fragment_add, container, false)
        val btnStartDate = view.btnStartDate
        btnStartDate.setOnClickListener {
            val picker = PersianDatePickerDialog(context)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(object : Listener{
                    @Override
                    override fun onDateSelected(persianCalendar: PersianCalendar?) {
                        val date = persianCalendar?.persianYear.toString()+
                                "/" + persianCalendar?.persianMonth + "/" +
                                persianCalendar?.persianDay
                        Toast.makeText(context, date, Toast.LENGTH_SHORT).show()
                        view.eventStartDate.setText(date)
                    }

                    @Override
                    override fun onDismissed() {

                    }
                })
            picker.show()
        }

        val btnEndDate = view.btnEndDate
        btnEndDate.setOnClickListener {
            val picker = PersianDatePickerDialog(context)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(object : Listener{
                    @Override
                    override fun onDateSelected(persianCalendar: PersianCalendar?) {
                        val date = persianCalendar?.persianYear.toString()+
                                "/" + persianCalendar?.persianMonth + "/" +
                                persianCalendar?.persianDay
                        Toast.makeText(context, date, Toast.LENGTH_SHORT).show()
                        view.eventEndDate.setText(date)
                    }

                    @Override
                    override fun onDismissed() {

                    }
                })
            picker.show()
        }


        return view
    }


}
