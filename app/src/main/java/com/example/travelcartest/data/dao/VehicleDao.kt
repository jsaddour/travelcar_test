package com.example.travelcartest.data.dao

import androidx.room.*
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.network.response.VehicleResponse
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface VehicleDao : BaseDao<Vehicle> {
    @Query("SELECT * FROM vehicle")
    fun observeVehicles(): Flowable<List<Vehicle>>

    @Query("SELECT * FROM vehicle")
    fun getVehicles(): List<Vehicle>

    @Query("SELECT * FROM vehicle WHERE id = :id")
    fun getVehicleById(id: Long): Maybe<Vehicle>

    @Transaction
    fun mergeVehiclesFromRemote(vehicles: List<VehicleResponse>) {
        val localList = getVehicles().sortedBy { it.year }
        val remoteList = vehicles.map { Vehicle(it) }.sortedBy { it.year }

        remoteList.forEach { vehicle ->
            localList.find { it.equals(vehicle) }?.let { localVehicle ->
                vehicle.id = localVehicle.id
                update(vehicle)
            }
                ?: insert(vehicle)
        }
    }
}