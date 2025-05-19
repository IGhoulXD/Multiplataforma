import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // para ignorar campos no mapeados
            })
        }
    }

    suspend fun getCharacters(): List<Character> {
        val response: CharacterResponse = client.get("https://rickandmortyapi.com/api/character").body()
        return response.results
    }
}
