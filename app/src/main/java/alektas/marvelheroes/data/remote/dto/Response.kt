package alektas.marvelheroes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Response(
    @Transient val attributionHTML: String,
    @Transient val attributionText: String,
    @Transient val code: Int,
    @Transient val copyright: String,
    @SerializedName("data") val `data`: Data,
    @Transient val etag: String,
    @Transient val status: String
)