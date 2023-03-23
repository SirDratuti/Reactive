package dao

import data.Currency
import data.Product
import data.User
import data.toOtherCurrency
import rx.Observable

class StoreService(
    private val storeDAO: StoreDAO,
) {
    fun registerUser(userId: Long, currency: Currency = Currency.USD) = storeDAO
        .addUser(
            user = User(
                id = userId,
                currency = currency,
            ),
        )

    fun addProduct(
        productId: Long,
        name: String,
        price: Double,
        currency: Currency = Currency.USD,
    ) = storeDAO.addProduct(
        product = Product(
            id = productId,
            name = name,
            price = price,
            currency = currency,
        ),
    )

    fun getProductForUser(userId: Long): Observable<Product> = Observable.combineLatest(
        storeDAO.getUser(userId),
        storeDAO.getProducts(),
    ) { user, product ->
        product.toOtherCurrency(user.currency)
    }
}