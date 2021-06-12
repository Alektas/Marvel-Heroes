package alektas.marvelheroes.di

import alektas.marvelheroes.BuildConfig
import alektas.marvelheroes.data.local.database.AppDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        if (BuildConfig.DEBUG) {
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        } else {
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
        }
            .build()
    
    companion object {
        
        private const val DB_NAME = "marvel"
    }
    
}