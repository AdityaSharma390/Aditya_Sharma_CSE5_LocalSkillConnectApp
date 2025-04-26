package fm.mrc.myapplication.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import fm.mrc.myapplication.data.models.ServiceType

object ServiceProviderUtils {
    fun getIndianizedServiceName(serviceType: ServiceType): String {
        return when (serviceType) {
            ServiceType.ELECTRICIAN -> "Bijli Mistri"
            ServiceType.PLUMBER -> "Naali Mistri"
            ServiceType.CARPENTER -> "Badhai Mistri"
            ServiceType.PAINTER -> "Rang Mistri"
            ServiceType.AC_TECHNICIAN -> "AC Mistri"
            ServiceType.HOME_CLEANER -> "Safai Karmi"
            ServiceType.GARDENER -> "Mali"
            ServiceType.APPLIANCE_REPAIR -> "Gadget Mistri"
            ServiceType.PEST_CONTROL -> "Keetnashak"
            ServiceType.MOVERS_AND_PACKERS -> "Samaan Dhakkan"
        }
    }

    fun getServiceIcon(serviceType: ServiceType) = when (serviceType) {
        ServiceType.ELECTRICIAN -> Icons.Default.ElectricBolt
        ServiceType.PLUMBER -> Icons.Default.Plumbing
        ServiceType.CARPENTER -> Icons.Default.Handyman
        ServiceType.PAINTER -> Icons.Default.Brush
        ServiceType.AC_TECHNICIAN -> Icons.Default.AcUnit
        ServiceType.HOME_CLEANER -> Icons.Default.CleaningServices
        ServiceType.GARDENER -> Icons.Default.Grass
        ServiceType.APPLIANCE_REPAIR -> Icons.Default.Build
        ServiceType.PEST_CONTROL -> Icons.Default.BugReport
        ServiceType.MOVERS_AND_PACKERS -> Icons.Default.LocalShipping
    }

    fun getAvailabilityIcon(isAvailable: Boolean) = if (isAvailable) {
        Icons.Default.CheckCircle
    } else {
        Icons.Default.Cancel
    }

    fun getAvailabilityColor(isAvailable: Boolean) = if (isAvailable) {
        androidx.compose.ui.graphics.Color.Green
    } else {
        androidx.compose.ui.graphics.Color.Red
    }

    fun getEmergencyServiceIcon(isAvailable: Boolean) = if (isAvailable) {
        Icons.Default.Emergency
    } else {
        Icons.Outlined.Emergency
    }
} 