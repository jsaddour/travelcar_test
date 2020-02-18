package com.example.travelcartest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.travelcartest.R

import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.android.synthetic.main.fragment_vehicle.*


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
                text_vehicle.text = vehicle.model
            })
        }
    }
}