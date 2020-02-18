package com.example.travelcartest.data.converters

import androidx.room.TypeConverter
import com.example.travelcartest.data.entity.Vehicle

class EquipmentsConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun toString(equipments: Vehicle.Equipments?): String? {
            return equipments?.toJson()
        }

        @TypeConverter
        @JvmStatic
        fun FromString(json: String?): Vehicle.Equipments? {
            if(json != null) {
                return Vehicle.Equipments.fromJson(json)
            }
            return null
        }
    }
}