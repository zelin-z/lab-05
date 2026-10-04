package com.example.listycity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    onDeleteCity: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }

    var selectedCity by remember { mutableStateOf<City?>(null) }
    var editedCityName by remember { mutableStateOf("") }
    var editedProvinceName by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        // ADD BUTTON
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields

                    if (showAddCityFields) {
                        selectedCity = null
                        editedCityName = ""
                        editedProvinceName = ""
                    }
                }
            ) {
                Text("+")
            }
        }

        // ADD CITY FORM
        if (showAddCityFields) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Add City",
                        fontSize = 22.sp
                    )

                    OutlinedTextField(
                        value = newCityName,
                        onValueChange = {
                            newCityName = it
                        },
                        label = {
                            Text("City")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newProvinceName,
                        onValueChange = {
                            newProvinceName = it
                        },
                        label = {
                            Text("Province")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                            if (
                                newCityName.isNotBlank() &&
                                newProvinceName.isNotBlank()
                            ) {

                                onAddCity(
                                    City(
                                        name = newCityName,
                                        province = newProvinceName
                                    )
                                )

                                newCityName = ""
                                newProvinceName = ""
                                showAddCityFields = false
                            }
                        }
                    ) {
                        Text("ADD CITY")
                    }
                }
            }
        }

        // UPDATE / DELETE FORM
        if (selectedCity != null) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Edit City",
                        fontSize = 22.sp
                    )

                    OutlinedTextField(
                        value = editedCityName,
                        onValueChange = {
                            editedCityName = it
                        },
                        label = {
                            Text("City")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editedProvinceName,
                        onValueChange = {
                            editedProvinceName = it
                        },
                        label = {
                            Text("Province")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {

                                val cityToUpdate = selectedCity

                                if (
                                    cityToUpdate != null &&
                                    editedCityName.isNotBlank() &&
                                    editedProvinceName.isNotBlank()
                                ) {

                                    onUpdateCity(
                                        cityToUpdate,
                                        City(
                                            name = editedCityName,
                                            province = editedProvinceName
                                        )
                                    )

                                    selectedCity = null
                                    editedCityName = ""
                                    editedProvinceName = ""
                                }
                            }
                        ) {
                            Text("UPDATE")
                        }

                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {

                                val cityToDelete = selectedCity

                                if (cityToDelete != null) {

                                    onDeleteCity(cityToDelete)

                                    selectedCity = null
                                    editedCityName = ""
                                    editedProvinceName = ""
                                }
                            }
                        ) {
                            Text("DELETE")
                        }
                    }
                }
            }
        }

        // CITY LIST
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            itemsIndexed(cities) { index, city ->

                CityRow(
                    city = city,
                    onClick = {
                        showAddCityFields = false

                        newCityName = ""
                        newProvinceName = ""

                        selectedCity = city
                        editedCityName = city.name
                        editedProvinceName = city.province
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {

        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {

    ListyCityTheme {

        CityListScreen(
            cities = listOf(
                City(
                    name = "Edmonton",
                    province = "AB"
                ),
                City(
                    name = "Vancouver",
                    province = "BC"
                ),
                City(
                    name = "Calgary",
                    province = "AB"
                )
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> },
            onDeleteCity = {}
        )
    }
}