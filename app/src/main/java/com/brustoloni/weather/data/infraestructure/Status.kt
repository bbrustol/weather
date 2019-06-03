package com.brustoloni.weather.data.infraestructure

sealed class NetworkState
class DataNotAvailable : NetworkState()
class UnexpectedError : NetworkState()
