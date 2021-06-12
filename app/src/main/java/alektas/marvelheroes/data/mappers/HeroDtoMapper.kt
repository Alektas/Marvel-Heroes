package alektas.marvelheroes.data.mappers

import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.data.local.database.entities.HeroEntity
import alektas.marvelheroes.data.remote.dto.HeroDto
import javax.inject.Inject

class HeroDtoMapper @Inject constructor() : Mapper<@JvmSuppressWildcards HeroDto, @JvmSuppressWildcards HeroEntity> {
    
    override fun map(from: HeroDto): HeroEntity = with(from) {
        HeroEntity(id, name, description, imageUrl = "${thumbnail.path}.${thumbnail.extension}")
    }
    
}