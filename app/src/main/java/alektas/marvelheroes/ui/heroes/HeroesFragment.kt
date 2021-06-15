package alektas.marvelheroes.ui.heroes

import alektas.marvelheroes.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alektas.marvelheroes.core.ui.BaseFragment
import alektas.marvelheroes.databinding.FragmentHeroesBinding
import alektas.marvelheroes.ui.heroes.adapters.HeroesAdapter
import alektas.marvelheroes.ui.heroes.adapters.HeroesLoadStateAdapter
import androidx.appcompat.app.AlertDialog
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
        val heroesAdapter = HeroesAdapter().apply {
            addLoadStateListener { loadState ->
                viewModel.onLoadStateChanged(loadState, itemCount)
            }
        }
        rvHeroes.apply {
            setHasFixedSize(true)
            adapter = heroesAdapter.withLoadStateHeaderAndFooter(
                header = HeroesLoadStateAdapter(heroesAdapter::retry),
                footer = HeroesLoadStateAdapter(heroesAdapter::retry)
            )
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
        
        viewModel.error
            .autoDispose(AndroidLifecycleScopeProvider.from(lifecycle))
            .subscribe { e ->
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.title_error))
                    .setMessage(e.localizedMessage ?: getString(R.string.error_unknown))
                    .setPositiveButton(R.string.btn_retry) { _, _ -> heroesAdapter.refresh() }
                    .setNeutralButton(android.R.string.cancel) { _, _ -> }
                    .create()
                dialog.show()
            }
    }
    
    companion object {
        
        fun newInstance() = HeroesFragment()
    }
    
}
