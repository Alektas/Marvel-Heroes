package alektas.marvelheroes.di

import alektas.marvelheroes.BuildConfig
import alektas.marvelheroes.R
import alektas.marvelheroes.data.remote.MarvelApi
import alektas.marvelheroes.data.remote.models.MarvelApiMetadata
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    
    @Provides
    @Singleton
    fun provideMarvelApi(retrofit: Retrofit): MarvelApi = retrofit.create()
    
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }
        .build()
    
    @Provides
    fun provideMarvelApiMetadata(@ApplicationContext context: Context): MarvelApiMetadata = with(context.resources) {
        MarvelApiMetadata(
            apiKey = getString(R.string.marvel_api_key),
            privateApiKey = getString(R.string.marvel_private_api_key)
        )
    }
    
}