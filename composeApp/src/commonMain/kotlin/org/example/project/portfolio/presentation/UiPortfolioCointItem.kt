package org.example.project.portfolio.presentation

data class UiPortfolioCoinItem(
    val id: String,
    val name: String,
    val iconUrl: String,
    val amountInUnitText: String,
    val amountInFiatText: String,
    val performancePercentText: String,
    val isPositive: Boolean
)
