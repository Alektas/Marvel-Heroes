package alektas.marvelheroes.ui.heroes

import alektas.marvelheroes.R
import alektas.marvelheroes.databinding.ItemHeroBinding
import alektas.marvelheroes.ui.heroes.models.HeroItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HeroesAdapter : PagingDataAdapter<HeroItem, HeroesAdapter.ViewHolder>(HeroDiffCallback) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
    
    class ViewHolder(private val binding: ItemHeroBinding): RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: HeroItem) = with(binding) {
            with(item) {
                tvName.text = name
                tvDescription.text = description
                ivImage.setImageDrawable(null)
                
                imageUrl ?: return@with
                Glide.with(root)
                    .load(imageUrl)
                    .centerCrop()
                    .thumbnail(0.3f)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivImage)
            }
        }
        
    }
    
    object HeroDiffCallback : DiffUtil.ItemCallback<HeroItem>() {
    
        override fun areItemsTheSame(oldItem: HeroItem, newItem: HeroItem): Boolean = oldItem.id == newItem.id
    
        override fun areContentsTheSame(oldItem: HeroItem, newItem: HeroItem): Boolean = oldItem == newItem
    
    }
    
}
