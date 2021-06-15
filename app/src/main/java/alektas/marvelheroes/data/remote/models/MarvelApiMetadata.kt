package alektas.marvelheroes.data.remote.models

import alektas.marvelheroes.data.utils.toMd5

data class MarvelApiMetadata(val apiKey: String, val privateApiKey: String) {
    
    fun hash(timestamp: Long): String = "${timestamp}${privateApiKey}${apiKey}".toMd5()
    
    override fun toString(): String = "MarvelApiMetaData(apiKey='$apiKey')"
    
}
