package org.example.project.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.example.project.coins.domain.GetCoinsListUseCase
import org.example.project.core.domain.Result

class CoinsListViewModel(private val getCoinsListUseCase: GetCoinsListUseCase) : ViewModel() {
    private val _state = MutableStateFlow(CoinState())
    val state = _state.onStart {

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
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
                                formattedPrice = coinItem.price.toString(), //TODO: formatFiat(coinItem.price),
                                formattedChange = coinItem.change.toString(), //TODO: formatChange(coinItem.change),
                                isPositive = coinItem.change >= 0
                            )
                        })
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        coins = emptyList(),
                        error = null //TODO: coinsResponse.error.toUiText()
                 )
                }
            }

        }
    }
}