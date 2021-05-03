import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {
        /** Retorna uma Instância do Client Retrofit para Requisições
         * @param path Caminho Principal da API
         */
        fun getRetrofitInstance(path : String) : Retrofit {
            return Retrofit.Builder()
                        .baseUrl(path)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
        }

        object ServiceBuilder {
            private val client = OkHttpClient.Builder().build()

            private val retrofit = Retrofit.Builder()
                .baseUrl("https://6086ce0da3b9c200173b6e8e.mockapi.io/api/v1/") // change this IP for testing by your actual machine IP
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            fun<T> buildService(service: Class<T>): T{
                return retrofit.create(service)
            }
        }
}