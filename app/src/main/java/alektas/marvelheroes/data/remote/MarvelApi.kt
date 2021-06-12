package alektas.marvelheroes.data.remote

import alektas.marvelheroes.data.remote.dto.Response
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun searchHeroes(
        @Query("nameStartsWith") query: String,
        @Query("orderBy") order: String = "name",
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 10,
        @Query("ts") timestamp: Long,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
    ): Single<Response>
    
}