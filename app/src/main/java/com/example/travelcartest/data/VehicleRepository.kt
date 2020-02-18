package com.example.travelcartest.data


import com.example.travelcartest.TravelCarTestApp
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.network.NetworkManager
import com.example.travelcartest.network.response.VehicleResponse
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object VehicleRepository {

    fun fetchVehicles(): Single<List<VehicleResponse>> {
        return NetworkManager.getInstance().getVehicles()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess { results ->
                TravelCarDb.getInstance(TravelCarTestApp.appContext()).vehicleDao()
                    .mergeVehiclesFromRemote(results)
            }
    }

    fun observeVehicles(): Flowable<List<Vehicle>> {
        return TravelCarDb.getInstance(TravelCarTestApp.appContext()).vehicleDao()
            .observeVehicles()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    fun getVehicleById(id: Long): Maybe<Vehicle> {
        return TravelCarDb.getInstance(TravelCarTestApp.appContext()).vehicleDao()
            .getVehicleById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }
}