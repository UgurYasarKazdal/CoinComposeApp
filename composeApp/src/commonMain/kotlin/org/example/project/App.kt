package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.coins.presentation.CoinsListScreen
import org.example.project.core.navigation.Buy
import org.example.project.core.navigation.Coins
import org.example.project.core.navigation.Portfolio
import org.example.project.core.navigation.Sell
import org.example.project.portfolio.presentation.PortfolioScreen
import org.example.project.theme.CoinRoutineTheme
import org.example.project.trade.presentation.buy.BuyScreen
import org.example.project.trade.presentation.sell.SellScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    CoinRoutineTheme {
        NavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            startDestination = Portfolio
        ) {
            composable<Portfolio> {
                PortfolioScreen(onCoinItemClicked = { coinId ->
                    navController.navigate(Sell(coinId))
                }, onDiscoverCoinsClicked = {
                    navController.navigate(Coins)
                })
            }

            composable<Coins> {
                CoinsListScreen(
                    onCoinClicked = { coinId ->
                        navController.navigate(Buy(coinId))
                    })
            }

            composable<Buy> { navBackStackEntry ->
                val coinId: String = navBackStackEntry.toRoute<Buy>().coinId
                BuyScreen(
                    coinId = coinId,
                    navigateToPortfolio = {
                        navController.navigate(Portfolio) {
                            popUpTo(Portfolio) { inclusive = true }
                        }
                    }
                )
            }

            composable<Sell> { navBackStackEntry ->
                val coinId: String = navBackStackEntry.toRoute<Sell>().coinId
                SellScreen(
                    coinId = coinId,
                    navigateToPortfolio = {
                        navController.navigate(Portfolio) {
                            popUpTo(Portfolio) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}