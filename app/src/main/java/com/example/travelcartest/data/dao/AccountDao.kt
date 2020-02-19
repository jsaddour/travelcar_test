package com.example.travelcartest.data.dao

import androidx.room.*
import com.example.travelcartest.data.entity.Account
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.network.response.VehicleResponse
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface AccountDao : BaseDao<Account> {


    @Query("SELECT * FROM account WHERE id = 1")
    fun getMyAccount(): Maybe<Account>

}