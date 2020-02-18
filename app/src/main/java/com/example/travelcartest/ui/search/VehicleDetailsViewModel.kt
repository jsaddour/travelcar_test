package com.example.travelcartest.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelcartest.data.VehicleRepository
import com.example.travelcartest.data.entity.Vehicle
import io.reactivex.rxkotlin.subscribeBy


class VehicleDetailsViewModel : ViewModel() {



    private val vehicle: MutableLiveData<SearchViewModel.VehicleModel> by lazy {
        MutableLiveData<SearchViewModel.VehicleModel>()
    }

    fun getVehicleById(id: Long) : LiveData<SearchViewModel.VehicleModel>{
        VehicleRepository.getVehicleById(id).subscribeBy {
            vehicle.postValue(SearchViewModel.VehicleModel(it))
        }
        return vehicle
    }

}