package alektas.marvelheroes.di

import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.data.local.database.entities.HeroEntity
import alektas.marvelheroes.data.mappers.HeroDtoMapper
import alektas.marvelheroes.data.mappers.HeroEntityMapper
import alektas.marvelheroes.data.remote.dto.HeroDto
import alektas.marvelheroes.domain.models.Hero
import alektas.marvelheroes.ui.heroes.mappers.HeroMapper
import alektas.marvelheroes.ui.heroes.models.HeroItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface HeroMappersModule {
    
    @Binds
    @ActivityRetainedScoped
    fun bindHeroDtoMapper(impl: HeroDtoMapper): Mapper<HeroDto, HeroEntity>
    
    @Binds
    @ActivityRetainedScoped
    fun bindHeroEntityMapper(impl: HeroEntityMapper): Mapper<HeroEntity, Hero>
    
    @Binds
    @ActivityRetainedScoped
    fun bindHeroMapper(impl: HeroMapper): Mapper<Hero, HeroItem>
    
}