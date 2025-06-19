package org.example.project.coins.domain.model

import org.example.project.core.domain.coins.Coin

data class CoinModel(
    val coin: Coin,
    val price: Double,
    val change: Double
)