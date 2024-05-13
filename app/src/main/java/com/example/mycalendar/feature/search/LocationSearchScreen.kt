package com.example.mycalendar.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.ui.component.ScheduleDetailFieldTemplate
import com.example.mycalendar.ui.component.SearchView
import com.example.mycalendar.ui.component.search.LocationItem

private const val TAG = "LocationSearchScreen"

@Composable
fun LocationSearchScreen(
    onNavigateBack: () -> Unit,
    // this screen depends totally on the parent viewModel
    // for re-usability, only pass the data and callbacks
    query: String,
    onQueryChange: (String) -> Unit,
    locationSearchUiState: LocationSearchUiState,
    onLocationSelected: (Location) -> Unit,
) {
    SearchView(
        placeHolder = "Search location",
        query = query,
        onQueryChange = onQueryChange,
        onNavigateBack = onNavigateBack
    ) {
        Spacer(modifier = Modifier.height(4.dp)) // top bar margin

        if (locationSearchUiState is LocationSearchUiState.Success) {
            if (!locationSearchUiState.isEmpty()) {
                ScheduleDetailFieldTemplate(
                    icon = {},
                    items = {
                        Text(
                            text = "Location results",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    })
                // display results
                locationSearchUiState.autocompleteLocations.forEach { location ->
                    LocationItem(location, onClick = {
                        onLocationSelected(location); onNavigateBack()
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                Text(
                    text = "We found nothing, try to search for other locations\n🫠",
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }

        } else if (locationSearchUiState is LocationSearchUiState.LoadFailed) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}