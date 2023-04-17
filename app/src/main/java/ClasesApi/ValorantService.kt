package ClasesApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ValorantService {
    @GET("v1/weapons")
    fun getWeapons(): Call<WeaponsResponse>
    @GET("v1/agents?isPlayableCharacter=true")
    fun getAgents(): Call<AgentsResponse>
    @GET("v1/contracts")
    fun getContracts(): Call<ContractsResponse>
    @GET("v1/maps")
    fun getMaps(): Call<MapsResponse>

    companion object {
        private const val BASE_URL = "https://valorant-api.com/"

        fun create(): ValorantService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ValorantService::class.java)
        }
    }
}