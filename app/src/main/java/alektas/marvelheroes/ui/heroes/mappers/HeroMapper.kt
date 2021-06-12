package alektas.marvelheroes.ui.heroes.mappers

import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.domain.models.Hero
import alektas.marvelheroes.ui.heroes.models.HeroItem
import javax.inject.Inject

class HeroMapper @Inject constructor() : Mapper<Hero, HeroItem> {
    
    override fun map(from: Hero): HeroItem = with(from) {
        HeroItem(id, name, description, imageUrl)
    }
    
}