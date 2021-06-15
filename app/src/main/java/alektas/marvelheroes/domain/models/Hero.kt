package alektas.marvelheroes.domain.models

data class Hero(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String?
)
