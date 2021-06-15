package alektas.marvelheroes.data.local.database.dao

import alektas.marvelheroes.data.local.database.entities.HeroEntity
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HeroesDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(heroes: List<HeroEntity>)

    @Query("SELECT id, name, description, image_url FROM heroes WHERE name LIKE :query")
    fun getHeroesSource(query: String): PagingSource<Int, HeroEntity>
    
    @Query("DELETE FROM heroes WHERE name LIKE :query")
    fun deleteByQuery(query: String)
    
}
