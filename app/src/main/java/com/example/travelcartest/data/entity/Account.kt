package com.example.travelcartest.data.entity

import androidx.room.*
import com.example.travelcartest.data.converters.EquipmentsConverter
import com.example.travelcartest.network.response.VehicleResponse
import com.example.travelcartest.utils.MoshiHelper
import java.io.IOException
import java.util.*

@Entity(
    tableName = "account"
)

data class Account(

    @PrimaryKey
    var id: Long,

    @ColumnInfo
    var firstName: String,

    @ColumnInfo
    var lastName: String,

    @ColumnInfo
    var address: String,


    @ColumnInfo
    var date: Date,

    @ColumnInfo
    var picture: String
) {

}