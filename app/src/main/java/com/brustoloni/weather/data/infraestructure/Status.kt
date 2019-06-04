package com.brustoloni.weather.data.infraestructure

sealed class NetworkState
object DataNotAvailable : NetworkState()
object UnexpectedError : NetworkState()
