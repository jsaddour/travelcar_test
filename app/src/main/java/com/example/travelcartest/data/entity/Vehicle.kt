package com.example.travelcartest.data.entity

import androidx.room.*
import com.example.travelcartest.data.converters.EquipmentsConverter
import com.example.travelcartest.network.response.VehicleResponse
import com.example.travelcartest.utils.MoshiHelper
import java.io.IOException
import java.util.*

@Entity(
    tableName = "vehicle"
)

data class Vehicle(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo
    var make: String,

    @ColumnInfo
    var model: String,

    @ColumnInfo
    var year: Int,

    @ColumnInfo
    var picture: String,

    @TypeConverters(EquipmentsConverter::class)
    @ColumnInfo
    var equipments: Equipments? // json
) {

    constructor(vehicleResponse: VehicleResponse) : this(
        0,
        vehicleResponse.make,
        vehicleResponse.model,
        vehicleResponse.year,
        vehicleResponse.picture,
        vehicleResponse.equipments?.let {
            Equipments(it)
        }
    )

    override fun equals(other: Any?): Boolean {
        //As remote objects don't have ids, we override equals in order to diff items in DB
        if (other !is Vehicle) {
            return false
        }
        return make == other.make &&
                model == other.model &&
                year == other.year

    }

    data class Equipments(val list: List<String>?) {
        fun toJson(): String {
            val adapter = MoshiHelper.ktBuilder().adapter(Equipments::class.java)
            return adapter.toJson(this)
        }

        companion object {
            fun fromJson(json: String): Equipments? {
                val adapter = MoshiHelper.ktBuilder().adapter(Equipments::class.java)
                try {
                    return adapter.fromJson(json) ?: throw NotImplementedError()
                } catch (e: IOException) {
                    e.printStackTrace()
                    throw NotImplementedError()
                }
            }

        }
    }
}