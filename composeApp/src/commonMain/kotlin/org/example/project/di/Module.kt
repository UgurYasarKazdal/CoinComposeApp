package org.example.project.di

import androidx.room.RoomDatabase
import io.ktor.client.HttpClient
import org.example.project.coins.data.remote.impl.CoinsRemoteDataSourceImpl
import org.example.project.coins.domain.GetCoinDetailsUseCase
import org.example.project.coins.domain.GetCoinPriceHistoryUseCase
import org.example.project.coins.domain.GetCoinsListUseCase
import org.example.project.coins.domain.api.CoinsRemoteDataSource
import org.example.project.coins.presentation.CoinsListViewModel
import org.example.project.core.database.portfolio.PortfolioDatabase
import org.example.project.core.database.portfolio.getPortfolioDatabase
import org.example.project.core.network.HttpClientFactory
import org.example.project.portfolio.data.PortfolioRepositoryImpl
import org.example.project.portfolio.domain.PortfolioRepository
import org.example.project.portfolio.presentation.PortfolioViewModel
import org.example.project.trade.domain.BuyCoinUseCase
import org.example.project.trade.domain.SellCoinUseCase
import org.example.project.trade.presentation.buy.BuyViewModel
import org.example.project.trade.presentation.sell.SellViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(
        sharedModule, platformModule
    )
}

expect val platformModule: Module

val sharedModule = module {
    //core
    single<HttpClient> { HttpClientFactory.create(get()) }

    //portfolio
    single {
        getPortfolioDatabase(get<RoomDatabase.Builder<PortfolioDatabase>>())
    }
    singleOf(::PortfolioRepositoryImpl).bind(PortfolioRepository::class)
    single { get<PortfolioDatabase>().portfolioDao() }
    single { get<PortfolioDatabase>().userBalanceDao() }
    viewModel { PortfolioViewModel(get()) }

    //coins list
    viewModel { CoinsListViewModel(get(), get()) }
    singleOf(::GetCoinsListUseCase)
    singleOf(::CoinsRemoteDataSourceImpl).bind(CoinsRemoteDataSource::class)
    singleOf(::GetCoinDetailsUseCase)
    singleOf(::GetCoinPriceHistoryUseCase)

    //trade
    singleOf(::BuyCoinUseCase)
    singleOf(::SellCoinUseCase)
    viewModel { BuyViewModel(get(), get(), get()) }
    viewModel { SellViewModel(get(), get(), get()) }
}

