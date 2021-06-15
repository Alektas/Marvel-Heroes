package alektas.marvelheroes.ui.heroes

import alektas.marvelheroes.core.data.mappers.Mapper
import alektas.marvelheroes.core.ui.BaseViewModel
import alektas.marvelheroes.domain.HeroesRepository
import alektas.marvelheroes.domain.models.Hero
import alektas.marvelheroes.ui.heroes.models.HeroItem
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val repository: HeroesRepository,
    private val heroMapper: @JvmSuppressWildcards Mapper<Hero, HeroItem>
) : BaseViewModel() {
    
    private val querySubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val placeholderSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)
    
    val placeholder: Observable<Boolean> = placeholderSubject
        .observeOn(AndroidSchedulers.mainThread())
    
    val heroes: Observable<PagingData<HeroItem>> = querySubject
        .debounce(SEARCH_DEBOUNCE_MILLIS, TimeUnit.MILLISECONDS)
        .map { it.trim() }
        .switchMap { query ->
            val isValidQuery = query.length >= SEARCH_MIN_LENGTH
            placeholderSubject.onNext(!isValidQuery)
            
            if (isValidQuery) {
                repository.getHeroesPager(query, SEARCH_PAGE_SIZE)
                    .map { data ->
                        data.map { heroMapper.map(it) }
                    }
                    .cachedIn(viewModelScope)
            } else {
                Observable.just(PagingData.empty())
            }
        }
    
    fun onQueryChanged(query: String) = querySubject.onNext(query)
    
    fun onLoadStateChanged(loadState: CombinedLoadStates, itemCount: Int) {
        placeholderSubject.onNext(
            loadState.source.refresh is LoadState.NotLoading
                && loadState.append.endOfPaginationReached
                && itemCount < 1
        )
    }
    
    companion object {
        
        private const val SEARCH_DEBOUNCE_MILLIS = 500L
        private const val SEARCH_MIN_LENGTH = 3
        private const val SEARCH_PAGE_SIZE = 10
    }
    
}