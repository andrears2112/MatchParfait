package com.example.matchparfait.view.components

import android.content.Context
import android.icu.util.Calendar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.matchparfait.R

class DatePickerBirthday @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val yearSpinner: Spinner
    private val monthSpinner: Spinner
    private val daySpinner: Spinner

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_date_picker_birthday, this, true)

        yearSpinner = findViewById(R.id.yearSpinner)
        monthSpinner = findViewById(R.id.monthSpinner)
        daySpinner = findViewById(R.id.daySpinner)

        setupYearSpinner()
        setupMonthSpinner()
        setupDaySpinner()
    }

    private fun setupYearSpinner() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear - 100..currentYear + 100).toList()
        val adapter = ArrayAdapter(context, R.layout.item_spinner, years)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        yearSpinner.adapter = adapter
        yearSpinner.setSelection(adapter.getPosition(currentYear))
    }

    private fun setupMonthSpinner() {
        val months = (1..12).toList()
        val adapter = ArrayAdapter(context, R.layout.item_spinner, months)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        monthSpinner.adapter = adapter
        monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))
    }

    private fun setupDaySpinner() {
        val days = (1..31).toList()
        val adapter = ArrayAdapter(context, R.layout.item_spinner, days)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        daySpinner.adapter = adapter
        daySpinner.setSelection(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1)
    }

    fun getSelectedYear(): Int {
        return yearSpinner.selectedItem as Int
    }

    fun getSelectedMonth(): Int {
        return monthSpinner.selectedItem as Int
    }

    fun getSelectedDay(): Int {
        return daySpinner.selectedItem as Int
    }

    fun setSelectedYear(year: Int) {
        val adapter = yearSpinner.adapter as ArrayAdapter<Int>
        yearSpinner.setSelection(adapter.getPosition(year))
    }

    fun setSelectedMonth(month: Int) {
        val adapter = monthSpinner.adapter as ArrayAdapter<Int>
        monthSpinner.setSelection(adapter.getPosition(month))
    }

    fun setSelectedDay(day: Int) {
        val adapter = daySpinner.adapter as ArrayAdapter<Int>
        daySpinner.setSelection(adapter.getPosition(day))
    }
}