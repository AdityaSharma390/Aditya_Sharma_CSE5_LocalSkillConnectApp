package fm.mrc.myapplication.data.models

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val userType: UserType = UserType.CLIENT,
    val profileImageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserType {
    CLIENT,
    SERVICE_PROVIDER
}

enum class ServiceType {
    ELECTRICIAN,
    PLUMBER,
    CARPENTER,
    PAINTER,
    AC_TECHNICIAN,
    HOME_CLEANER,
    GARDENER,
    APPLIANCE_REPAIR,
    PEST_CONTROL,
    MOVERS_AND_PACKERS
}

data class ServiceProvider(
    val businessName: String,
    val businessDescription: String,
    val servicesOffered: List<ServiceType>,
    val experienceYears: Int,
    val hourlyRate: Double,
    val location: Location,
    val availability: Availability,
    val workingHours: WorkingHours,
    val portfolioImages: List<String> = emptyList(),
    val certifications: List<String> = emptyList(),
    val isAvailableForJob: Boolean = true,
    val rating: Double = 0.0,
    val totalReviews: Int = 0,
    val isVerified: Boolean = false,
    val languagesSpoken: List<String> = listOf("Hindi", "English"),
    val emergencyServiceAvailable: Boolean = false
)

data class Client(
    val uid: String = "",
    val address: String = "",
    val savedProviders: List<String> = emptyList(), // List of provider UIDs
    val bookingHistory: List<String> = emptyList() // List of booking IDs
)

data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
)

data class Availability(
    val workingHours: Map<String, WorkingHours> = emptyMap(),
    val isAvailable: Boolean = true
)

data class WorkingHours(
    val start: String = "09:00",
    val end: String = "17:00",
    val isWorking: Boolean = true
)

data class Booking(
    val id: String = "",
    val clientId: String = "",
    val providerId: String = "",
    val service: String = "",
    val date: Long = 0,
    val status: BookingStatus = BookingStatus.PENDING,
    val price: Double = 0.0,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class BookingStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    COMPLETED,
    CANCELLED
} 