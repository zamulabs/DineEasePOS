package com.zamulabs.dineeasepos.dashboard

// UI state for the Dashboard
data class DashboardUiState(
    val totalSalesToday: String = "$2,500",
    val ordersProcessed: String = "150",
    val pendingOrders: String = "5",
    val cashVsOnline: String = "60% / 40%",
    val topSelling: List<TopSellingItem> = sampleTopSelling,
    val recentOrders: List<RecentOrder> = sampleRecentOrders,
)

sealed interface DashboardUiEvent {
    data object Refresh: DashboardUiEvent
}

data class TopSellingItem(
    val item: String,
    val qty: String,
    val revenue: String,
)

data class RecentOrder(
    val id: String,
    val status: String,
    val customer: String,
    val time: String,
    val total: String,
)

private val sampleTopSelling = listOf(
    TopSellingItem("Margherita Pizza", "50", "$500"),
    TopSellingItem("Chicken Caesar Salad", "40", "$400"),
    TopSellingItem("Spaghetti Carbonara", "30", "$300"),
    TopSellingItem("Tiramisu", "25", "$250"),
    TopSellingItem("Iced Latte", "20", "$200"),
)

private val sampleRecentOrders = listOf(
    RecentOrder("#12345", "Preparing", "Alex Turner", "10:00 AM", "$25"),
    RecentOrder("#12346", "Ready", "Olivia Bennett", "10:15 AM", "$30"),
    RecentOrder("#12347", "Delivered", "Ethan Carter", "10:30 AM", "$40"),
    RecentOrder("#12348", "Preparing", "Sophia Davis", "10:45 AM", "$15"),
    RecentOrder("#12349", "Ready", "Liam Evans", "11:00 AM", "$20"),
)
