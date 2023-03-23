import com.mongodb.rx.client.MongoClients
import dao.StoreDAO
import dao.StoreService
import io.RequestHandler
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServer
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import io.reactivex.netty.protocol.http.server.HttpServerResponse

fun main() {
    val client = MongoClients.create("mongodb://localhost:27017")
    val database = client.getDatabase("hw4")

    val handler = RequestHandler(
        storeService = StoreService(
            storeDAO = StoreDAO(
                users = database.getCollection("users"),
                products = database.getCollection("products"),
            ),
        ),
    )

    HttpServer
        .newServer(8080)
        .start { request: HttpServerRequest<ByteBuf>, response: HttpServerResponse<ByteBuf> ->
            response.writeString(
                handler.handleRequest(request)
                    .map { result -> result.toString() }
            )
        }
        .awaitShutdown()
}