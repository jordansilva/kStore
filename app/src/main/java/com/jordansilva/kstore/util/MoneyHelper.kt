package com.jordansilva.kstore.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object MoneyHelper {
    private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()
    private val currencyMap = mutableMapOf<String, Currency>()

    private fun getCurrency(currencyCode: String): Currency? {
        if (!currencyMap.containsKey(currencyCode)) {
            try {
                currencyMap[currencyCode] = Currency.getInstance(Locale("", currencyCode))
            } catch (ex: Exception) {
                return null
            }
        }

        return currencyMap[currencyCode]
    }

    fun formatPrice(currencyCode: String, value: BigDecimal): String {
        return getCurrency(currencyCode)?.let {
            numberFormat.currency = it
            numberFormat.format(value)
        } ?: value.toPlainString()
    }
}