package org.example.project.trade.domain

import kotlinx.coroutines.flow.first
import org.example.project.core.domain.DataError
import org.example.project.core.domain.EmptyResult
import org.example.project.core.domain.coins.Coin
import org.example.project.portfolio.domain.PortfolioCoinModel
import org.example.project.portfolio.domain.PortfolioRepository

class BuyCoinUseCase(
    private val portfolioRepository: PortfolioRepository,
) {
    suspend fun buyCoin(
        coin: Coin,
        amountInFiat: Double,
        price: Double,
    ): EmptyResult<DataError> {
        val balance = portfolioRepository.cashBalanceFlow().first()
        if (balance < amountInFiat) {
            return org.example.project.core.domain.Result.Error(DataError.Local.INSUFFICIENT_FUNDS)
        }

        val existingCoinResult = portfolioRepository.getPortfolioCoin(coin.id)
        val existingCoin = when (existingCoinResult) {
            is org.example.project.core.domain.Result.Success -> existingCoinResult.data
            is org.example.project.core.domain.Result.Error -> return org.example.project.core.domain.Result.Error(existingCoinResult.error)
        }
        val amountInUnit = amountInFiat / price
        if (existingCoin != null) {
            val newAmountOwned = existingCoin.ownedAmountInUnit + amountInUnit
            val newTotalInvestment = existingCoin.ownedAmountInFiat + amountInFiat
            val newAveragePurchasePrice = newTotalInvestment / newAmountOwned
            portfolioRepository.savePortfolioCoin(
                existingCoin.copy(
                    ownedAmountInUnit = newAmountOwned,
                    ownedAmountInFiat = newTotalInvestment,
                    averagePurchasePrice = newAveragePurchasePrice
                )
            )
        } else {
            portfolioRepository.savePortfolioCoin(
                PortfolioCoinModel(
                    coin = coin,
                    performancePercent = 0.0,
                    averagePurchasePrice = price,
                    ownedAmountInFiat = amountInFiat,
                    ownedAmountInUnit = amountInUnit
                )
            )
        }
        portfolioRepository.updateCashBalance(balance - amountInFiat)
        return org.example.project.core.domain.Result.Success(Unit)
    }
}