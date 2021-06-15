package alektas.marvelheroes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count") val count: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("results") val results: List<HeroDto>,
    @SerializedName("total") val total: Int
)