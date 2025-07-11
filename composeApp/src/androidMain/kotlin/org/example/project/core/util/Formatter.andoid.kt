package org.example.project.core.util

import java.text.DecimalFormat

actual fun formatFiat(amount: Double, showDecimal: Boolean): String {
    val formater = when {
        showDecimal.not() -> DecimalFormat("#,###")
        amount >= 1 -> DecimalFormat("#,###.00")
        amount >= 0.01 -> DecimalFormat("0.00")
        else -> DecimalFormat("0.00000000")
    }
    return "$ " + formater.format(amount)
}

actual fun formatCoinUnit(amount: Double, symbol: String): String {
    return DecimalFormat("0.000000000").format(amount) + " $symbol"
}

actual fun formatPercentage(amount: Double): String {
    val prefix = if (amount >= 0) "+" else ""
    return prefix + DecimalFormat("0.00").format(amount) + " %"
}