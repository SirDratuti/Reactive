package data

sealed interface Currency {
    object USD : Currency
    object RUB : Currency
    object CAD : Currency
    object EUR : Currency
}

fun getCurrency(raw: String) : Currency? = when(raw.lowercase()) {
    "usd" -> Currency.USD
    "rub" -> Currency.RUB
    "cad" -> Currency.CAD
    "eur" -> Currency.EUR
    else -> null
}

fun Currency.toUSD() = when (this) {
    Currency.USD -> 1.0
    Currency.RUB -> 0.013
    Currency.EUR -> 1.08
    Currency.CAD -> 0.73
}

fun Currency.USD.toCurrency(currency: Currency) = when (currency) {
    Currency.USD -> 1.0
    Currency.RUB -> 1.0 / 0.013
    Currency.EUR -> 1.0 / 1.08
    Currency.CAD -> 1.0 / 0.73
}