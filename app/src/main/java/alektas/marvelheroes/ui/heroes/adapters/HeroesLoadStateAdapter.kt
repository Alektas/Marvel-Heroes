package alektas.marvelheroes.ui.heroes.adapters

import alektas.marvelheroes.databinding.ItemHeroLoaderBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class HeroesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HeroesLoadStateAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ViewHolder(ItemHeroLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false), retry)
    
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)
    
    class ViewHolder(
        private val binding: ItemHeroLoaderBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                tvError.text = loadState.error.localizedMessage
                btnRetry.setOnClickListener { retry() }
            } else {
                btnRetry.setOnClickListener(null)
            }
            
            pbLoading.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error
        }
        
    }
    
}