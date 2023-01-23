package com.example.rickandmortydb.di

import com.example.rickandmortydb.data.remote.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCharacterApi(): CharacterApi{
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}