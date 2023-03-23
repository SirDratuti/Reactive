package data

import org.bson.Document

data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val currency: Currency = Currency.USD,
)

fun Product.toOtherCurrency(currency: Currency): Product {
    val usdPrice = price * this.currency.toUSD()
    val usdCurrency = Currency.USD
    val newCurrencyPrice = usdPrice * (usdCurrency.toCurrency(currency))
    return copy(
        price = newCurrencyPrice,
        currency = currency,
    )
}

fun Product.toDocument(): Document = Document()
    .append("id", id)
    .append("name", name)
    .append("price", price)
    .append("currency", currency)