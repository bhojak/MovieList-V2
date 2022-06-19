package com.bhojak.tmdb.data.util

import java.lang.Exception

data class DataOrException<T, Boolean, E:Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var e: Exception? = null
)
