package alektas.marvelheroes.data

import alektas.marvelheroes.core.data.mappers.ListMapper
import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.data.local.database.AppDatabase
import alektas.marvelheroes.data.local.database.entities.HeroEntity
import alektas.marvelheroes.data.local.database.entities.HeroesPagingOffsetEntity
import alektas.marvelheroes.data.remote.MarvelApi
import alektas.marvelheroes.data.remote.dto.HeroDto
import alektas.marvelheroes.data.remote.dto.Response
import alektas.marvelheroes.data.remote.models.MarvelApiMetadata
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class HeroesRemoteMediator(
    private val query: String,
    private val metadata: MarvelApiMetadata,
    private val api: MarvelApi,
    private val database: AppDatabase,
    private val mapper: Mapper<HeroDto, HeroEntity>
) : RxRemoteMediator<Int, HeroEntity>() {
    
    private val heroesDao = database.getHeroesDao()
    private val offsetsDao = database.getHeroesPagingOffsetsDao()
    
    override fun loadSingle(loadType: LoadType, state: PagingState<Int, HeroEntity>): Single<MediatorResult> {
        val loadOffsetSingle = when (loadType) {
            LoadType.REFRESH -> Single.just(HeroesPagingOffsetEntity(query, 0))
            LoadType.PREPEND -> return Single.just(MediatorResult.Success(true))
            LoadType.APPEND -> offsetsDao.remoteOffsetByQuery(query)
        }
        
        return loadOffsetSingle
            .subscribeOn(Schedulers.io())
            .flatMap { offsetEntity ->
                if (loadType != LoadType.REFRESH && offsetEntity.nextOffset == null) {
                    return@flatMap Single.just(MediatorResult.Success(true))
                }
                
                val timestamp = System.currentTimeMillis()
                val hash = metadata.hash(timestamp)
                api.searchHeroes(
                    query,
                    offset = offsetEntity.nextOffset ?: 0,
                    timestamp = timestamp,
                    apiKey = metadata.apiKey,
                    hash = hash
                )
                    .map(Function<Response, MediatorResult> { response ->
                        with(response.data) {
                            val nextOffset = offset + count
                            updateDatabase(loadType, results, nextOffset)
                            MediatorResult.Success(total <= nextOffset)
                        }
                    })
            }
            .onErrorResumeNext { e ->
                Log.e("MarvelRemoteMediator", "Failed to load marvel heroes", e)
                if (e is IOException || e is HttpException) {
                    return@onErrorResumeNext Single.just(MediatorResult.Error(e))
                }
                Single.error(e)
            }
    }
    
    private fun updateDatabase(loadType: LoadType, results: List<HeroDto>, nextOffset: Int) =
        database.runInTransaction {
            if (loadType == LoadType.REFRESH) {
                heroesDao.deleteByQuery(query)
                offsetsDao.deleteByQuery(query)
            }
    
            heroesDao.insertAll(ListMapper(mapper).map(results))
            offsetsDao.insertOrReplace(HeroesPagingOffsetEntity(query, nextOffset))
        }
    
}
