package alektas.marvelheroes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HeroDto(
    @SerializedName("comics") val comics: Comics,
    @SerializedName("description") val description: String,
    @SerializedName("events") val events: Events,
    @SerializedName("id") val id: Long,
    @SerializedName("modified") val modified: String,
    @SerializedName("name") val name: String,
    @SerializedName("resourceURI") val resourceUri: String,
    @SerializedName("series") val series: Series,
    @SerializedName("stories") val stories: Stories,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("urls") val urls: List<Url>
)