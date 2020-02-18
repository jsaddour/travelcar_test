package com.example.travelcartest.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelcartest.data.VehicleRepository
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.network.ErrorManager
import com.example.travelcartest.utils.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy

class SearchViewModel : ViewModel() {

    private val vehicles: MutableLiveData<List<VehicleModel>> by lazy {
        MutableLiveData<List<VehicleModel>>().also {
            VehicleRepository.observeVehicles()
                .subscribeBy {
                        results -> vehicles.postValue(results.map { VehicleModel(it) }) }

        }
    }

     val error: SingleLiveEvent<String> by lazy {
        SingleLiveEvent<String>()
    }

    fun getVehicles(): LiveData<List<VehicleModel>> {
        return vehicles
    }


    fun fetchVehicles() {
        VehicleRepository.fetchVehicles().subscribeBy(onError = {
            ErrorManager.getRequestError(it).message?.let { message -> error.postValue(message) }
        })
    }

    data class VehicleModel(

        var id: Long,

        var make: String,

        var model: String,

        var year: Int,

        var picture: String,

        var equipments: Vehicle.Equipments?
    ) {

        constructor(vehicle: Vehicle) : this(
            vehicle.id,
            vehicle.make,
            vehicle.model,
            vehicle.year,
            vehicle.picture,
            vehicle.equipments?.let {
                Vehicle.Equipments(it.list)
            }
        )

    }
}