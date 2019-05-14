import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import kotlinx.coroutines.delay
import java.net.URL

suspend fun main() {
    val client = HttpClient(Apache) {

        engine {
            socketTimeout = 0

            customizeClient {
                setMaxConnPerRoute(0)
                setMaxConnTotal(0)
            }
        }
    }

    var attempt = 0

    while(true) {
        val response = client.call("https://tickets.fcbayern.com/Internetverkauf/EventList.aspx") {
            method = HttpMethod.Get
        }.response

        println("[${++attempt}] Status Code: [${response.status.value}]")

        response.close()

        delay(5000)
    }
}