package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class ServiceCategory(
    val title: String,
    val icon: ImageVector
)

data class Provider(
    val name: String,
    val profession: String,
    val rating: Float,
    val reviewCount: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }
    
    val categories = listOf(
        ServiceCategory("Electricians", Icons.Default.Build),
        ServiceCategory("Plumbers", Icons.Default.Settings),
        ServiceCategory("HVAC", Icons.Default.Home),
        ServiceCategory("Handyman", Icons.Default.Build)
    )
    
    val providers = listOf(
        Provider("John Doe", "Electrician", 4.8f, 120),
        Provider("Jane Smith", "Plumber", 4.9f, 85),
        Provider("Mike Johnson", "HVAC", 4.7f, 95)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("SkillConnect") },
                actions = {
                    IconButton(onClick = { /* TODO: Notifications */ }) {
                        Icon(Icons.Default.Notifications, "Notifications")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* TODO: Implement search */ },
                active = searchActive,
                onActiveChange = { searchActive = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search for services...") },
                leadingIcon = { Icon(Icons.Default.Search, "Search") }
            ) {
                // Search suggestions can go here
            }

            // Emergency Services Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Emergency",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        "Emergency Services",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FilledTonalButton(onClick = { /* TODO: Emergency services */ }) {
                        Text("Call Now")
                    }
                }
            }

            // Service Categories
            Text(
                "Service Categories",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(200.dp),
                userScrollEnabled = false
            ) {
                items(categories) { category ->
                    ServiceCategoryCard(category)
                }
            }

            // Featured Providers
            Text(
                "Featured Providers",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = true
            ) {
                items(providers) { provider ->
                    ProviderCard(provider)
                }
            }
        }
    }
}

@Composable
fun ServiceCategoryCard(category: ServiceCategory) {
    ElevatedCard(
        onClick = { /* TODO: Navigate to category */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                category.icon,
                contentDescription = category.title,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                category.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ProviderCard(provider: Provider) {
    ElevatedCard(
        modifier = Modifier.width(200.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Provider Image placeholder
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                provider.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                provider.profession,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    "${provider.rating} (${provider.reviewCount} reviews)",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
} 