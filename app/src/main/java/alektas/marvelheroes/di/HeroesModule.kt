package alektas.marvelheroes.di

import alektas.marvelheroes.data.HeroesRepositoryImpl
import alektas.marvelheroes.domain.HeroesRepository
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Module
@InstallIn(ActivityRetainedComponent::class)
interface HeroesModule {
    
    @Binds
    @ActivityRetainedScoped
    fun provideHeroesRepository(impl: HeroesRepositoryImpl): HeroesRepository
    
}