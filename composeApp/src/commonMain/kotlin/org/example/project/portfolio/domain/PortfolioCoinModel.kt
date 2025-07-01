package org.example.project.portfolio.domain

import org.example.project.core.domain.coins.Coin

data class PortfolioCoinModel(
    val coin: Coin,
    val performancePercent: Double,
    val averagePurchasePrice: Double,
    val ownedAmountInUnit: Double,
    val ownedAmountFiat: Double,
)
