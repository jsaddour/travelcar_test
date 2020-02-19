package com.example.travelcartest.data


import com.example.travelcartest.TravelCarTestApp
import com.example.travelcartest.data.entity.Account
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.network.NetworkManager
import com.example.travelcartest.network.response.VehicleResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object AccountRepository {


    fun getMyAccount(): Maybe<Account> {
        return TravelCarDb.getInstance(TravelCarTestApp.appContext()).accountDao()
            .getMyAccount()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    fun saveAccount(account: Account) {
        Completable.fromAction {
            TravelCarDb.getInstance(TravelCarTestApp.appContext()).accountDao().insert(account)
        }.subscribeOn(Schedulers.io()).subscribe {  }


    }
}