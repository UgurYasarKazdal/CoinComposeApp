package org.example.project.coins.domain.api

import org.example.project.coins.data.remote.dto.CoinDetailsResponseDto
import org.example.project.coins.data.remote.dto.CoinPriceHistoryResponseDto
import org.example.project.coins.data.remote.dto.CoinResponseDto
import org.example.project.coins.data.remote.dto.CoinsListDto
import org.example.project.coins.data.remote.dto.CoinsResponseDto
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

interface CoinsRemoteDataSource {
    suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote>

    suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote>

    suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote>
}