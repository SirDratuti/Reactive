package data

import org.bson.Document

class User(
    val id: Long,
    val currency: Currency = Currency.USD,
)

fun User.toDocument(): Document = Document()
    .append("id", id)
    .append("currency", currency)