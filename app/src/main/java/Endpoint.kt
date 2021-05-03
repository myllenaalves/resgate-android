import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Endpoint {
    @GET("pets")
    fun getPets() : Call<List<Pets>>

    @Headers("Content-Type: application/json")
    @POST("pets")
    fun postPets(@Body userData: Pets) : Call<Pets>
}

