package com.example.travelcartest.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelcartest.data.AccountRepository
import com.example.travelcartest.data.entity.Account
import com.example.travelcartest.ui.search.SearchViewModel
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

class MyAccountViewModel : ViewModel() {

    private val account: MutableLiveData<Account> by lazy {
        MutableLiveData<Account>().also {
            AccountRepository.getMyAccount().subscribeBy {
                account.postValue(it)
            }
        }
    }

    fun getMyAccount() : LiveData<Account>{
        return account
    }

    fun saveAccount(firstName: String, lastName: String, birthDate: Date, address: String, picture: String) {
        AccountRepository.saveAccount(Account(1, firstName, lastName, address, birthDate, picture ))
    }
}