package org.example.project.portfolio.data.mapper

import org.example.project.core.domain.coins.Coin
import org.example.project.portfolio.data.local.PortfolioCoinEntity
import org.example.project.portfolio.domain.PortfolioCoinModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun PortfolioCoinEntity.toPortfolioCoinModel(
    currentPrice: Double
): PortfolioCoinModel {
    return PortfolioCoinModel(
        coin = Coin(
            id = coinId,
            name = name,
            symbol = symbol,
            iconUrl = iconUrl
        ),
        performancePercent = ((currentPrice - averagePurchasePrice) / averagePurchasePrice) * 100,
        averagePurchasePrice = averagePurchasePrice,
        ownedAmountInUnit = amountOwned,
        ownedAmountInFiat = amountOwned * currentPrice,
    )
}

@OptIn(ExperimentalTime::class)
fun PortfolioCoinModel.toPortfolioCoinEntity(): PortfolioCoinEntity {
    return PortfolioCoinEntity(
        coinId = coin.id,
        name = coin.name,
        symbol = coin.symbol,
        iconUrl = coin.iconUrl,
        amountOwned = ownedAmountInUnit,
        averagePurchasePrice = averagePurchasePrice,
        timestamp = Clock.System.now().toEpochMilliseconds()
    )
}
