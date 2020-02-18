package com.example.travelcartest.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.annotations.CheckReturnValue
import java.util.*

object MoshiHelper {

    @CheckReturnValue
    fun ktBuilder(addDateAdapter: Boolean = false): Moshi {
        val builder = Moshi.Builder()

        // Kotlin adapter
        builder.add(KotlinJsonAdapterFactory())

        // Date adapter
        if (addDateAdapter) {
            builder.add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        }

        return builder.build()
    }
}