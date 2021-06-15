package alektas.marvelheroes.data.local.database.dao

import alektas.marvelheroes.data.local.database.entities.HeroesPagingOffsetEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface HeroesPagingOffsetsDao {
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOrReplace(offset: HeroesPagingOffsetEntity)

  @Query("SELECT * FROM remote_keys WHERE `query` = :query")
  fun remoteOffsetByQuery(query: String): Single<HeroesPagingOffsetEntity>

  @Query("DELETE FROM remote_keys WHERE `query` = :query")
  fun deleteByQuery(query: String)
  
}