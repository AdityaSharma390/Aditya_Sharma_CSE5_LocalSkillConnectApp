package fm.mrc.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fm.mrc.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientHomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }
    
    val categories = listOf(
        ServiceCategory("Electricians", Icons.Outlined.ElectricBolt),
        ServiceCategory("Plumbers", Icons.Outlined.Plumbing),
        ServiceCategory("HVAC", Icons.Filled.AcUnit),
        ServiceCategory("Handyman", Icons.Filled.Handyman),
        ServiceCategory("Carpenters", Icons.Outlined.Carpenter),
        ServiceCategory("Painters", Icons.Filled.Brush)
    )
    
    val providers = listOf(
        Provider("John Doe", "Electrician", 4.8f, 120),
        Provider("Jane Smith", "Plumber", 4.9f, 85),
        Provider("Mike Johnson", "HVAC", 4.7f, 95)
    )
    
    val recentJobs = listOf(
        RecentJob("Electrical Repair", "Completed", "May 15, 2023", "John Doe"),
        RecentJob("Pipe Fixing", "In Progress", "May 18, 2023", "Jane Smith"),
        RecentJob("AC Installation", "Scheduled", "May 20, 2023", "Mike Johnson")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Engineering,
                            contentDescription = "Logo",
                            tint = PrimaryBlue
                        )
                        Text(
                            "SkillConnect",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Notifications */ }) {
                        Icon(Icons.Default.Notifications, "Notifications")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* TODO: Navigate to Home */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* TODO: Navigate to Search */ },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* TODO: Navigate to Bookings */ },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Bookings") },
                    label = { Text("Bookings") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* TODO: Navigate to Profile */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
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
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    inputFieldColors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            ) {
                // Search suggestions can go here
            }

            // User Welcome Message
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryBlue
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Background gradient
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        PrimaryBlue,
                                        SecondaryLightBlue
                                    )
                                )
                            )
                    )
                    
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Welcome back, Alex",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                            Text(
                                "What service do you need today?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                        FilledTonalButton(
                            onClick = { /* TODO: Create job posting */ },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Post a Job",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Post Job")
                        }
                    }
                }
            }

            // Recent Jobs
            Text(
                "Your Recent Jobs",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recentJobs) { job ->
                    RecentJobCard(job)
                }
            }

            // Service Categories
            Text(
                "Service Categories",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
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

            // Top Rated Providers
            Text(
                "Top Rated Providers",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
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
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RecentJobCard(job: RecentJob) {
    val statusColor = when (job.status) {
        "Completed" -> SuccessGreen
        "In Progress" -> WarningYellow
        else -> InfoBlue
    }
    
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    job.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Provider",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        job.providerName,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Date",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        job.date,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = statusColor.copy(alpha = 0.2f),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            when (job.status) {
                                "Completed" -> Icons.Default.Check
                                "In Progress" -> Icons.Default.Sync
                                else -> Icons.Default.Schedule
                            },
                            contentDescription = "Status",
                            modifier = Modifier.size(16.dp),
                            tint = statusColor
                        )
                        Text(
                            job.status,
                            style = MaterialTheme.typography.labelMedium,
                            color = statusColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceCategoryCard(category: ServiceCategory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                category.icon,
                contentDescription = category.name,
                modifier = Modifier.size(32.dp),
                tint = PrimaryBlue
            )
            Text(
                category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ProviderCard(provider: Provider) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Provider",
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp),
                tint = PrimaryBlue
            )
            
            Text(
                provider.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                provider.service,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp),
                    tint = WarningYellow
                )
                Text(
                    provider.rating.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Text(
                "$${provider.hourlyRate}/hr",
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class ServiceCategory(
    val name: String,
    val icon: ImageVector
)

data class Provider(
    val name: String,
    val service: String,
    val rating: Float,
    val hourlyRate: Int
)

data class RecentJob(
    val title: String,
    val status: String,
    val date: String,
    val providerName: String
) 