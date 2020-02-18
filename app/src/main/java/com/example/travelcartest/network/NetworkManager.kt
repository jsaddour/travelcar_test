package com.example.travelcartest.network

import com.example.travelcartest.network.response.VehicleResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class NetworkManager {


    private val apiService: ApiService

    fun getVehicles(): Single<List<VehicleResponse>> {
        return newThread(apiService.getVehicles())
    }


    private fun <T> newThread(request: Single<T>): Single<T> {
        return request.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .mapNetworkErrors()
    }

    init {
        val baseUrl = "https://gist.githubusercontent.com/"

        apiService = ServiceGeneratorAPI.createService(baseUrl, ApiService::class.java)
    }

    companion object {
        // Singleton
        private var instance: NetworkManager? = null

        fun getInstance(): NetworkManager {
            if (instance == null) {
                instance = NetworkManager()
            }
            return instance as NetworkManager
        }
    }
}
