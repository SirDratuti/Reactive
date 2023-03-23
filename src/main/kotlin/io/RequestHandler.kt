package io

import io.netty.buffer.ByteBuf;
import dao.StoreService
import data.Currency
import data.getCurrency
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import response.Response
import rx.Observable

class RequestHandler(
    private val storeService: StoreService,
) {
    fun handleRequest(request: HttpServerRequest<ByteBuf>): Observable<Response> =
        when (request.decodedPath) {
            "/register" -> {
                val userId = request
                    .getParameter("userId")
                    ?.toLongOrNull()
                    ?: error("no userId in query")
                val currency = request
                    .getParameter("currency")
                    ?.let(::getCurrency)
                    ?: Currency.USD
                storeService.registerUser(userId, currency)
            }

            "/addProduct" -> {
                val productId = request
                    .getParameter("id")
                    ?.toLongOrNull()
                    ?: error("no userId in query")
                val name = request
                    .getParameter("name")
                    ?: error("no name in query")
                val price = request
                    .getParameter("price")
                    ?.toDoubleOrNull()
                    ?: error("no price in query")
                val currency = request
                    .getParameter("currency")
                    ?.let(::getCurrency)
                    ?: Currency.USD

                storeService.addProduct(
                    productId = productId,
                    name = name,
                    price = price,
                    currency = currency,
                )
            }

            "/products" -> {
                val userId = request
                    .getParameter("userId")
                    ?.toLongOrNull()
                    ?: error("no userId in query")
                storeService.getProductForUser(userId).map { Response.Result(it) }
            }

            else -> Observable.just(Response.Failure)
        }

    private fun HttpServerRequest<ByteBuf>.getParameter(name: String) = queryParameters
        .getOrDefault(name, null)
        ?.firstOrNull()
}