package alektas.marvelheroes.data

import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.data.local.database.AppDatabase
import alektas.marvelheroes.data.local.database.dao.HeroesDao
import alektas.marvelheroes.data.local.database.entities.HeroEntity
import alektas.marvelheroes.data.remote.MarvelApi
import alektas.marvelheroes.data.remote.dto.HeroDto
import alektas.marvelheroes.data.remote.models.MarvelApiMetadata
import alektas.marvelheroes.domain.HeroesRepository
import alektas.marvelheroes.domain.models.Hero
import androidx.paging.*
import androidx.paging.rxjava3.observable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class HeroesRepositoryImpl @Inject constructor(
    private val apiMetadata: MarvelApiMetadata,
    private val api: MarvelApi,
    private val database: AppDatabase,
    private val heroDtoMapper:  @JvmSuppressWildcards Mapper<HeroDto, HeroEntity>,
    private val heroEntityMapper:  @JvmSuppressWildcards Mapper<HeroEntity, Hero>,
) : HeroesRepository {
    
    private val heroesDao: HeroesDao = database.getHeroesDao()
    
    override fun getHeroesPager(query: String, pageSize: Int): Observable<PagingData<Hero>> = Pager(
        PagingConfig(pageSize, initialLoadSize = pageSize),
        remoteMediator = HeroesRemoteMediator(query, apiMetadata, api, database, heroDtoMapper)
    ) {
        heroesDao.getHeroesSource("%${query}%")
    }.observable
        .map { data ->
            data.map { heroEntityMapper.map(it) }
        }
    
}
