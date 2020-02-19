package com.example.travelcartest.ui.account

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.travelcartest.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_my_account.*
import java.text.SimpleDateFormat
import android.app.Activity
import android.net.Uri
import androidx.lifecycle.Observer

import com.bumptech.glide.Glide
import java.util.*


class MyAccountFragment : Fragment() {

    private lateinit var myAccountViewModel: MyAccountViewModel
    private var selectedPicture: Uri? = null
    private val myCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myAccountViewModel =
            ViewModelProviders.of(this).get(MyAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_account, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDatePicker()
        photo_container.setOnClickListener {
            pickFromGallery()
        }
        myAccountViewModel.getMyAccount().observe(this, Observer { account ->
            first_name_input.setText( account.firstName)
            last_name_input.setText( account.lastName)
            myCalendar.time = account.date
            updateBirthDateLabel(myCalendar, birthdate_input)
            address_input.setText(account.address)
            Glide
                .with(context!!)
                .load(Uri.parse(account.picture))
                .into(photo)
        })

        save_button.setOnClickListener {
            myAccountViewModel.saveAccount(first_name_input.text.toString(), last_name_input.text.toString(),
                myCalendar.time, address_input.text.toString(), selectedPicture.toString())
        }
    }

    private fun setDatePicker() {



        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateBirthDateLabel(myCalendar, birthdate_input)
            }

        birthdate_input.setOnClickListener {
            DatePickerDialog(
                context!!, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateBirthDateLabel(myCalendar: Calendar, dateEditText: TextInputEditText) {
        val myFormat: String = "dd-MMM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
        dateEditText.setText(sdf.format(myCalendar.time))
    }

    private fun pickFromGallery() {
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    selectedPicture = data!!.data
                    Glide
                        .with(context!!)
                        .load(selectedPicture)
                        .into(photo)
                }
            }
    }

    companion object{
        val GALLERY_REQUEST_CODE = 42
    }
}