package org.example.project.coins.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto(val data:CoinsListDto)

@Serializable
data class CoinsListDto(val coins:List<CoinItemDto>)

@Serializable
data class CoinItemDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: Double,
    val change: Double,
    val rank: Int,
)