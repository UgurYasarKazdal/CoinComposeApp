package org.example.project.trade.presentation.mapper

import org.example.project.core.domain.coins.Coin
import org.example.project.trade.presentation.common.UiTradeCoinItem

fun UiTradeCoinItem.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    iconUrl = iconUrl,
)