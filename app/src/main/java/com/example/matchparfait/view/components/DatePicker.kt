package com.example.matchparfait.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.matchparfait.R
import java.util.Calendar

class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val yearSpinner: Spinner
    private val monthSpinner: Spinner

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_date_picker, this, true)

        yearSpinner = findViewById(R.id.yearSpinner)
        monthSpinner = findViewById(R.id.monthSpinner)

        setupYearSpinner()
        setupMonthSpinner()
    }

    private fun setupYearSpinner() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear - 100..currentYear + 100).toList()
        val adapter = ArrayAdapter(context, R.layout.item_spinner, years)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        yearSpinner.adapter = adapter
        yearSpinner.setSelection(years.indexOf(currentYear))
    }

    private fun setupMonthSpinner() {
        val months = (1..12).toList()
        val adapter = ArrayAdapter(context, R.layout.item_spinner, months)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        monthSpinner.adapter = adapter
        monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))
    }

    fun getSelectedYear(): String {
        val fullYear = yearSpinner.selectedItem as Int
        val lastTwoDigits = fullYear % 100
        return if (lastTwoDigits < 10) {
            "0$lastTwoDigits"
        } else {
            lastTwoDigits.toString()
        }
    }

    fun getSelectedMonth(): String {
        val month = monthSpinner.selectedItem as Int
        return if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
    }

    fun setSelectedYear(year: Int) {
        val adapter = yearSpinner.adapter as ArrayAdapter<Int>
        yearSpinner.setSelection(adapter.getPosition(year))
    }

    fun setSelectedMonth(month: Int) {
        val adapter = monthSpinner.adapter as ArrayAdapter<Int>
        monthSpinner.setSelection(adapter.getPosition(month))
    }
}