package com.example.travelcartest.ui.search

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.travelcartest.R


import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_vehicle.*
import kotlinx.android.synthetic.main.fragment_vehicle.vehicle_make_label
import kotlinx.android.synthetic.main.fragment_vehicle.vehicle_model_label


class VehicleDetailsFragment : Fragment() {

    private var vehicleId: Long? = null
    private lateinit var vehicleDetailsViewModel: VehicleDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vehicleDetailsViewModel =
            ViewModelProviders.of(this).get(VehicleDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_vehicle, container, false)
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments?.let { VehicleDetailsFragmentArgs.fromBundle(it) }
        args?.let {
            vehicleId = args.id
        }
        vehicleId?.let { id ->
            vehicleDetailsViewModel.getVehicleById(id).observe(this, Observer { vehicle ->
                vehicle_make_label.text = vehicle.make
                vehicle_model_label.text = vehicle.model
                vehicle_year_label.text = vehicle.year.toString()
                val equipments = vehicle.equipments?.list?.joinToString (prefix = " ", separator = ", ") ?: ""
                vehicle_equipement_label.text = "${getString(R.string.equipements)}$equipments"
                Glide
                    .with(context!!)
                    .load(Uri.parse(vehicle.picture))
                    .into(vehicle_img)
            })
        }
    }
}