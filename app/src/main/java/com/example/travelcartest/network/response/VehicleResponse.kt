package com.example.travelcartest.network.response

import com.squareup.moshi.Json



data class VehicleResponse(
    @field:Json(name = "make") val make: String,
    @field:Json(name = "model") val model: String,
    @field:Json(name = "year") val year: Int,
    @field:Json(name = "picture") var picture: String,
    @field:Json(name = "equipments") var equipments: List<String>?
) {

}
