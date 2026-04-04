package com.ootd.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ootd.app.domain.model.Occasion
import com.ootd.app.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("今日穿搭", modifier = Modifier.padding(8.dp))

        WeatherCard(
            weather = uiState.weather,
            loading = uiState.weatherLoading,
            onRefresh = { viewModel.refreshWeather() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("选择场景", modifier = Modifier.padding(8.dp))

        OccasionSelector(
            selectedOccasion = uiState.selectedOccasion,
            onOccasionSelected = { viewModel.selectOccasion(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (uiState.recommendedOutfits.isNotEmpty()) {
            Text("推荐搭配", modifier = Modifier.padding(8.dp))
            OutfitRecommendationList(outfits = uiState.recommendedOutfits)
        }

        if (uiState.error != null) {
            Text("Error: ${uiState.error}", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun WeatherCard(
    weather: com.ootd.app.domain.model.Weather?,
    loading: Boolean,
    onRefresh: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else if (weather != null) {
                Text("${weather.location}: ${weather.temperature}°C ${weather.condition}")
                Text("湿度: ${weather.humidity}% | 风力: ${weather.windLevel}级")
            }
            Button(onClick = onRefresh) {
                Text("刷新天气")
            }
        }
    }
}

@Composable
fun OccasionSelector(
    selectedOccasion: Occasion,
    onOccasionSelected: (Occasion) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(Occasion.entries) { occasion ->
            Button(
                onClick = { onOccasionSelected(occasion) },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(occasion.name)
            }
        }
    }
}

@Composable
fun OutfitRecommendationList(outfits: List<com.ootd.app.domain.model.Outfit>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        outfits.forEach { outfit ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Outfit ${outfit.id.take(8)}")
                        Text("${outfit.occasion} | ${outfit.season}")
                    }
                }
            }
        }
    }
}
