package com.example.mycalendar.feature.search

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.ui.component.SearchView
import com.example.mycalendar.ui.component.search.LocationItem
import com.example.mycalendar.ui.theme.MyCalendarTheme

@Composable
fun LocationSearchScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val locations = listOf(
        Location(displayName = "Tower of London, Tower Hill, Tower Liberty, Whitechapel, London, Greater London, England, EC3N 4AB, United Kingdom"),
        Location(displayName = "London, Greater London, England, EC3N 4AB, United Kingdom"),
        Location(
            displayName = "Tower Bridge, Tower Bridge, Tower Liberty, Wapping, London, Greater London, England, SE1 2LY, United Kingdom"
        )
    )
    SearchView(items = locations, itemFactory = {
        LocationItem(it)
        Spacer(modifier = Modifier.height(4.dp))
    }, onNavigateBack = onNavigateBack)
}

@Preview
@Composable
fun LocationSearchViewPreview() {
    MyCalendarTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val locations = listOf(
                Location(displayName = "Tower of London, Tower Hill, Tower Liberty, Whitechapel, London, Greater London, England, EC3N 4AB, United Kingdom"),
                Location(displayName = "London, Greater London, England, EC3N 4AB, United Kingdom"),
                Location(
                    displayName = "Tower Bridge, Tower Bridge, Tower Liberty, Wapping, London, Greater London, England, SE1 2LY, United Kingdom"
                )
            )
            SearchView(
                items = locations,
                itemFactory = {
                    LocationItem(it)
                    Spacer(modifier = Modifier.height(4.dp))
                },
                onNavigateBack = { }
            )
        }
    }
}