package alektas.marvelheroes.domain

import alektas.marvelheroes.domain.models.Hero
import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Observable

interface HeroesRepository {
    fun getHeroesPager(query: String, pageSize: Int): Observable<PagingData<Hero>>
}