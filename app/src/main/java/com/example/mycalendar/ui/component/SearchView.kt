package com.example.mycalendar.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchView(
    onNavigateBack: () -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            SearchViewTopBar(
                placeHolder = "Add location",
                query = query,
                onQueryChange = { onQueryChange(it) },
                onNavigateBack = onNavigateBack,
                onClearQuery = { onQueryChange("") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Column(content = content)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewTopBar(
    placeHolder: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Surface(shadowElevation = 3.dp) {
        TopAppBar(
            title = {
                NoDecorationTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = placeHolder,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(24.dp)
                        .clickable { onNavigateBack() }
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close",
                    modifier = Modifier.padding(end = 16.dp)
                        .clickable { onClearQuery() }
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}