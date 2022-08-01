package com.kkk.baseapp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ActivityRegisterBinding
import com.kkk.mylibrary.ui.activity.BaseActivity
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity


class RegisterActivity : BaseViewBindingActivity<ActivityRegisterBinding>() {

    companion object{
        private const val IE_NAME = "IE_NAME"
        fun newIntent(context: Context, name:String): Intent {
            val intent = Intent(context,RegisterActivity::class.java)
            intent.putExtra(IE_NAME,name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(IE_NAME)
        val mPickDateButton = findViewById<Button>(R.id.pick_date_button)
        val mShowSelectedDateText = findViewById<TextView>(R.id.show_selected_date)

        val constraints= CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now()).build()
        val materialDateBuilder =
            MaterialDatePicker.Builder.datePicker().setCalendarConstraints(constraints)

        materialDateBuilder.setTitleText("SELECT A DATE")

        val materialDatePicker = materialDateBuilder.build()

        mPickDateButton.setOnClickListener {

            materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        }

        materialDatePicker.addOnPositiveButtonClickListener{
                    mShowSelectedDateText.text = "Selected Date is : " + materialDatePicker.headerText
        }

        val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId // Returns View.NO_ID if nothing is checked.
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Responds to child RadioButton checked/unchecked
        }

// To check a radio button
        binding.radioButton1.isChecked = true

// To listen for a radio button's checked/unchecked state changes
        binding.radioButton1.setOnCheckedChangeListener { buttonView, isChecked ->
            // Responds to radio button being checked/unchecked
        }

        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (binding.acCountry as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding
        get() = ActivityRegisterBinding::inflate

    override fun setup() {
    }

    override fun observers() {
    }

    override fun listeners() {
    }
}