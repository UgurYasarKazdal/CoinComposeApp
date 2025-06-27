package org.example.project.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.coins.domain.GetCoinPriceHistoryUseCase
import org.example.project.coins.domain.GetCoinsListUseCase
import org.example.project.core.domain.Result
import org.example.project.core.util.formatFiat
import org.example.project.core.util.formatPercentage
import org.example.project.core.util.toUiText

class CoinsListViewModel(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val getCoinPriceHistoryUseCase: GetCoinPriceHistoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CoinState())
    val state = _state.onStart {
        getAllCoins()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value
    )

    private suspend fun getAllCoins() {
        when (val coinsResponse = getCoinsListUseCase.execute()) {
            is Result.Success -> {
                _state.update {
                    CoinState(
                        coins = coinsResponse.data.map { coinItem ->
                            UiCoinListItem(
                                id = coinItem.coin.id,
                                name = coinItem.coin.name,
                                symbol = coinItem.coin.symbol,
                                iconUrl = coinItem.coin.iconUrl,
                                formattedPrice = formatFiat(coinItem.price),
                                formattedChange = formatPercentage(coinItem.change),
                                isPositive = coinItem.change >= 0
                            )
                        })
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        coins = emptyList(), error = coinsResponse.error.toUiText()
                    )
                }
            }

        }
    }

    fun onCoinLongPressed(coinId: String) {
        _state.update {
            it.copy(
                chartState = UiChartState(sparkLine = emptyList(), isLoading = true)
            )
        }

        viewModelScope.launch {
            when (val priceHistoryResponse = getCoinPriceHistoryUseCase.execute(coinId)) {
                is Result.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            chartState = UiChartState(
                                sparkLine = priceHistoryResponse.data?.sortedBy { it?.timestamp }
                                    ?.map { it?.price ?: 0.0 } ?: emptyList(),
                                isLoading = false,
                                coinName = _state.value.coins.find { it.id == coinId }?.name.orEmpty()
                            )
                        )
                    }
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(
                            chartState = UiChartState(
                                sparkLine = emptyList(), isLoading = false
                            )
                        )
                    }
                }
            }
        }
    }

    fun onDismissChart() {
        _state.update {
            it.copy(chartState = null)
        }
    }
}