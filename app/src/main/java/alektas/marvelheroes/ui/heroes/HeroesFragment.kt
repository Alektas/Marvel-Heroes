package alektas.marvelheroes.ui.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alektas.marvelheroes.core.ui.BaseFragment
import alektas.marvelheroes.databinding.FragmentHeroesBinding
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HeroesFragment : BaseFragment<FragmentHeroesBinding, HeroesViewModel>() {
    
    override val viewModel: HeroesViewModel by viewModels()
    
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentHeroesBinding {
        return FragmentHeroesBinding.inflate(inflater, container, false)
    }
    
    override val initView: FragmentHeroesBinding.(v: View) -> Unit = {
        val heroesAdapter = HeroesAdapter()
        heroesAdapter.addLoadStateListener { loadState ->
            viewModel.onLoadStateChanged(loadState, heroesAdapter.itemCount)
        }
        rvHeroes.apply {
            setHasFixedSize(true)
            adapter = heroesAdapter
        }
        
        etHeroesQuery.doOnTextChanged { text, _, _, _ -> viewModel.onQueryChanged(text?.toString() ?: "") }
        
        viewModel.heroes
            .autoDispose(AndroidLifecycleScopeProvider.from(lifecycle))
            .subscribe {
                heroesAdapter.submitData(lifecycle, it)
            }
    
        viewModel.placeholder
            .autoDispose(AndroidLifecycleScopeProvider.from(lifecycle))
            .subscribe {
                tvHeroesPlaceholder.isVisible = it
            }
    }
    
    companion object {
        
        fun newInstance() = HeroesFragment()
    }
    
}
