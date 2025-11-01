package com.example.week78.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week78.R
import com.example.week78.ui.viewModel.WeatherUIState
import com.example.week78.ui.viewModel.WeatherViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.week78.ui.model.Cuaca



class Soal1View {
    @Composable
    fun WeatherApp(viewModel: WeatherViewModel = viewModel()) {
        var city by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                city = city,
                onCityChange = { city = it },
                onSearchClick = { viewModel.searchWeather(city) }
            )

            when (val state = viewModel.uiState) {
                is WeatherUIState.Searching -> SearchingContent()
                is WeatherUIState.Loading -> LoadingContent()
                is WeatherUIState.Success -> CityDisplayContent(state.data)
                is WeatherUIState.Error -> ErrorContent(state.message)
            }
        }
    }

    @Composable
    private fun SearchBar(
        city: String,
        onCityChange: (String) -> Unit,
        onSearchClick: () -> Unit
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = onCityChange,
                label = { Text("Enter City Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.LightGray.copy(0.2f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = onSearchClick,
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray.copy(0.2f)
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon(Icons.Default.Search, null)
                Text("Search")
            }
        }
    }

    @Composable
    private fun SearchingContent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(100.dp)
            )
            Text(
                text = "Search for a city to get started",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Enter a city name above",
                color = Color.LightGray
            )
        }
    }

    @Composable
    private fun LoadingContent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading weather data...", color = Color.LightGray)
        }
    }

    @Composable
    private fun ErrorContent(message: String) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(100.dp)
            )
            Text(
                text = "Oops! Something went wrong",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = message,
                color = Color.LightGray
            )
        }
    }

    @Composable
    private fun CityDisplayContent(weatherData: Cuaca) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Location and City
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, tint = Color.White)
                Spacer(modifier = Modifier.width(5.dp))
                Text(weatherData.kota, color = Color.LightGray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(5.dp))

            // City Name and Date
            Text(
                java.text.SimpleDateFormat(" MMMM dd", java.util.Locale.getDefault())
                    .format(java.util.Date()),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Last Updated
            Text(
                "Updated as of: ${java.text.SimpleDateFormat("HH:mm a", java.util.Locale.getDefault())
                    .format(java.util.Date())}",
                color = Color.LightGray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(100.dp))

            // Weather Info and Panda Image Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Weather Column
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Replace the iconByUrl section with:
                    weatherData.weatherIconUrl.let { url ->
                        if (url.isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(url),
                                contentDescription = "Weather Icon",
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }



                    Spacer(modifier = Modifier.height(10.dp))

                    // Weather Description
                    Text(
                        weatherData.weatherDescription, // You can get this from weatherData if you add it
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    // Temperature
                    Text(
                        "${weatherData.suhu}°C",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Panda Image Column
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(getPandaImageForWeather(weatherData.awan)),
                        contentDescription = "Weather Panda",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            // Weather Details Grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ComponentsCard("HUMIDITY", "${weatherData.kelembapan}%", R.drawable.icon_humidity, Modifier.weight(1f))
                    ComponentsCard("WIND", "${weatherData.angin} km/h", R.drawable.icon_wind, Modifier.weight(1f))
                    ComponentsCard("FEELS LIKE", "${weatherData.suhuPerasaan}°C", R.drawable.icon_feels_like, Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ComponentsCard("RAINFALL", "${weatherData.hujanTurun} mm", R.drawable.icon_raining, Modifier.weight(1f))
                    ComponentsCard("PRESSURE", "${weatherData.tekananUdara} hPa", R.drawable.time, Modifier.weight(1f))
                    ComponentsCard("CLOUDS", "${weatherData.awan}%", R.drawable.icon_cloud, Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(R.drawable.sunrise), contentDescription = null, modifier = Modifier.size(70.dp))
                    Text("SUNRISE", color = Color.LightGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(weatherData.sunrise, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(R.drawable.sunset), contentDescription = null, modifier = Modifier.size(70.dp))
                    Text("SUNSET", color = Color.LightGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(weatherData.sunset, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    private fun getPandaImageForWeather(cloudiness: Int): Int {
        return when {
            cloudiness < 20 -> R.drawable.sunny
            cloudiness < 50 -> R.drawable.cloudy
            else -> R.drawable.rain
        }
    }
}
