package fm.mrc.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fm.mrc.myapplication.ui.theme.*

@Composable
fun UserTypeSelectionScreen(
    onProviderSelected: () -> Unit,
    onClientSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PrimaryBlue.copy(alpha = 0.1f),
                        BackgroundLight
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                PrimaryBlue.copy(alpha = 0.2f),
                                PrimaryBlue.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Engineering,
                    contentDescription = "SkillConnect Logo",
                    modifier = Modifier.size(64.dp),
                    tint = PrimaryBlue
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Welcome Text
            Text(
                "Welcome to SkillConnect",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = PrimaryBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                "Please select your account type",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )
            
            // Selection Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Service Provider Card
                ElevatedCard(
                    onClick = onProviderSelected,
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = SurfaceLight
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Top corner decoration
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        bottomEnd = 80.dp
                                    )
                                )
                                .background(PrimaryBlue.copy(alpha = 0.1f))
                        )
                        
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.HomeRepairService,
                                    contentDescription = "Service Provider",
                                    modifier = Modifier.size(36.dp),
                                    tint = PrimaryBlue
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                "Service Provider",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                                color = TextPrimary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            Text(
                                "Offer your skills and services",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = TextSecondary
                            )
                        }
                    }
                }
                
                // Client Card
                ElevatedCard(
                    onClick = onClientSelected,
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = SurfaceLight
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Top corner decoration
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        bottomEnd = 80.dp
                                    )
                                )
                                .background(PrimaryPurple.copy(alpha = 0.1f))
                        )
                        
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryPurple.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Client",
                                    modifier = Modifier.size(36.dp),
                                    tint = PrimaryPurple
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                "Client",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                                color = TextPrimary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            Text(
                                "Find and hire skilled professionals",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
            
            // Footer Text
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                "Connect with local service providers for all your needs",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                "Local Skill Connect v1.0",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
} 