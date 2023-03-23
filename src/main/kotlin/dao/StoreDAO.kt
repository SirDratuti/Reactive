package dao

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoCollection
import com.mongodb.rx.client.Success
import data.Currency
import data.Product
import data.User
import data.toDocument
import org.bson.Document
import response.Response
import rx.Observable

class StoreDAO(
    private val users: MongoCollection<Document>,
    private val products: MongoCollection<Document>,
) {
    fun addProduct(product: Product): Observable<Response> = products
        .insertOne(product.toDocument())
        .toResponseObservable()

    fun addUser(user: User): Observable<Response> = users
        .insertOne(user.toDocument())
        .toResponseObservable()

    fun getUser(userId: Long): Observable<User> = users
        .find(Filters.eq("id", userId))
        .toObservable()
        .map { document ->
            User(
                id = document.getLong("id"),
                currency = document.get("currency", Currency.USD),
            )
        }

    fun getProduct(productId: Long): Observable<Product> = products
        .find(Filters.eq("id", productId))
        .toObservable()
        .map { document ->
            Product(
                id = document.getLong("id"),
                name = document.getString("name"),
                price = document.getDouble("price"),
                currency = document.get("currency", Currency.USD),
            )
        }

    fun getProducts(): Observable<Product> = products
        .find()
        .toObservable()
        .map { document ->
            Product(
                id = document.getLong("id"),
                name = document.getString("name"),
                price = document.getDouble("price"),
                currency = document.get("currency", Currency.USD),
            )
        }

    private fun Observable<Success>.toResponseObservable() = map {
        it?.let { Response.Success } ?: Response.Failure
    }
}