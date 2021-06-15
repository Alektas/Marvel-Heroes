package alektas.marvelheroes.data.local.database

import alektas.marvelheroes.data.local.database.dao.HeroesDao
import alektas.marvelheroes.data.local.database.dao.HeroesPagingOffsetsDao
import alektas.marvelheroes.data.local.database.entities.HeroEntity
import alektas.marvelheroes.data.local.database.entities.HeroesPagingOffsetEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HeroEntity::class, HeroesPagingOffsetEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun getHeroesDao(): HeroesDao
    
    abstract fun getHeroesPagingOffsetsDao(): HeroesPagingOffsetsDao
    
}
