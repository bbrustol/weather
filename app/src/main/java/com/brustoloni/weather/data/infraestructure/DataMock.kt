package com.brustoloni.weather.data.infraestructure

class DataMock {

    companion object {
//        fun createList() = WeatherResponse(
//            listOf(createPoi())
//        )
//
//        fun createPoi() = Poi(
//            createCoordinate(),
//            FLEETTYPE,
//            HEADING,
//            ID
//        )
//        fun createCoordinate() = Coordinate(
//             0.0,
//            0.0
//        )

        private const val FLEETTYPE: String = "weather"
        private const val HEADING: Double = 0.0
        private const val ID: Int = 0
        const val ILLEGAL_ARGUMENT : String = "Illegal argument"

//        val myInitPositions: List<Double> = listOf(53.46036882190762, 9.909716434648558, 53.668806556867445, 10.019908942943804)

    }
}