package org.example.project.coins.domain

import org.example.project.coins.data.mapper.toPriceModel
import org.example.project.coins.domain.api.CoinsRemoteDataSource
import org.example.project.coins.domain.model.PriceModel
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result
import org.example.project.core.domain.map

class GetCoinPriceHistoryUseCase(private val client: CoinsRemoteDataSource) {
    suspend fun execute(coinId: String): Result<List<PriceModel?>?, DataError.Remote> =
        client.getPriceHistory(coinId).map { dto ->
            dto.data?.history?.map { it?.toPriceModel() }
        }
}