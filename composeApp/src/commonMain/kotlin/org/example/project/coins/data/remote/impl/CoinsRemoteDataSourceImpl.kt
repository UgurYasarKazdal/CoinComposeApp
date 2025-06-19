package org.example.project.coins.data.remote.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.example.project.coins.data.remote.dto.CoinDetailsResponseDto
import org.example.project.coins.data.remote.dto.CoinPriceHistoryResponseDto
import org.example.project.coins.data.remote.dto.CoinResponseDto
import org.example.project.coins.domain.api.CoinsRemoteDataSource
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result
import org.example.project.core.network.safeCall

private const val BASE_URL = "https://api.coinranking.com/v2"

class CoinsRemoteDataSourceImpl(private val httpClient: HttpClient) : CoinsRemoteDataSource {
    override suspend fun getListOfCoins(): Result<CoinResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coins")
    }}

    override suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId/history")
        }
    }

    override suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId")
        }
    }
}