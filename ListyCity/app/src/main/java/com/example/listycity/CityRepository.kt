package com.example.listycity

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore

class CityRepository {

    private val db = FirebaseFirestore.getInstance()
    private val citiesCollection = db.collection("cities")

    private val _cities = mutableStateListOf<City>()

    val cities: List<City>
        get() = _cities

    init {
        listenForCities()
    }

    private fun listenForCities() {
        citiesCollection.addSnapshotListener { snapshot, error ->

            if (error != null || snapshot == null) {
                return@addSnapshotListener
            }

            _cities.clear()

            for (document in snapshot.documents) {
                val city = City(
                    id = document.id,
                    name = document.getString("name") ?: "",
                    province = document.getString("province") ?: ""
                )

                _cities.add(city)
            }
        }
    }

    fun addCity(city: City) {
        val cityData = hashMapOf(
            "name" to city.name,
            "province" to city.province
        )

        citiesCollection.add(cityData)
    }

    fun updateCity(oldCity: City, updatedCity: City) {
        if (oldCity.id.isBlank()) {
            return
        }

        val cityData = hashMapOf(
            "name" to updatedCity.name,
            "province" to updatedCity.province
        )

        citiesCollection
            .document(oldCity.id)
            .set(cityData)
    }

    fun deleteCity(city: City) {
        if (city.id.isBlank()) {
            return
        }

        citiesCollection
            .document(city.id)
            .delete()
    }
}