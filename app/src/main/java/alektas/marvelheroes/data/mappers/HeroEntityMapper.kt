package alektas.marvelheroes.data.mappers

import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.data.local.database.entities.HeroEntity
import alektas.marvelheroes.domain.models.Hero
import javax.inject.Inject

class HeroEntityMapper @Inject constructor() : Mapper<HeroEntity, Hero> {
    
    override fun map(from: HeroEntity): Hero = with(from) {
        Hero(id, name, description, imageUrl)
    }
    
}