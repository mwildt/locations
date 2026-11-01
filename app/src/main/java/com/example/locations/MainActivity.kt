package com.example.locations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationsApp()
        }
    }
}

data class Location(
    val name: String,
    val radiusMeters: Float,
)

private val defaultLocations = listOf(
    Location("Zuhause", 100f),
    Location("Arbeit", 250f),
    Location("Fitnessstudio", 150f),
)

@Composable
fun LocationsApp() {
    val locations = remember { mutableStateListOf<Location>() }
    if (locations.isEmpty()) {
        locations.addAll(defaultLocations)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Locations",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            items(locations, key = { it.name }) { location ->
                LocationCard(
                    location = location,
                    onRadiusChange = { newRadius ->
                        val index = locations.indexOfFirst { it.name == location.name }
                        if (index >= 0) {
                            locations[index] = location.copy(radiusMeters = newRadius)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LocationCard(
    location: Location,
    onRadiusChange: (Float) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Radius")
                Text(text = "${location.radiusMeters.roundToInt()} m")
            }
            Slider(
                value = location.radiusMeters,
                onValueChange = onRadiusChange,
                valueRange = 50f..1000f,
                steps = 18
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationsAppPreview() {
    var previewRadius by remember { mutableStateOf(100f) }
    Surface(modifier = Modifier.fillMaxSize()) {
        LocationCard(
            location = Location("Zuhause", previewRadius),
            onRadiusChange = { previewRadius = it }
        )
    }
}
