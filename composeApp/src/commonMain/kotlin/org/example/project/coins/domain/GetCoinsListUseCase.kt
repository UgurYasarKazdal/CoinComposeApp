package org.example.project.coins.domain

import org.example.project.coins.data.mapper.toCoinModel
import org.example.project.coins.domain.api.CoinsRemoteDataSource
import org.example.project.coins.domain.model.CoinModel
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result
import org.example.project.core.domain.map

class GetCoinsListUseCase(private val client: CoinsRemoteDataSource) {
    suspend fun execute(): Result<List<CoinModel>, DataError.Remote> =
        client.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel() }
        }
}