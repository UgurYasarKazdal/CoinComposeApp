package org.example.project.coins.presentation

import org.jetbrains.compose.resources.StringResource

data class CoinState(
    val error: StringResource?=null,
    val coins: List<UiCoinListItem> = emptyList(),
)
