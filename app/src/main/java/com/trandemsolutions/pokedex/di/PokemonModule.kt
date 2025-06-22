package com.trandemsolutions.pokedex.di

import com.trandemsolutions.pokedex.data.IPokemonRepository
import com.trandemsolutions.pokedex.data.IPokemonService
import com.trandemsolutions.pokedex.data.PokemonRepository
import com.trandemsolutions.pokedex.data.PokemonService
import com.trandemsolutions.pokedex.util.ILogger
import com.trandemsolutions.pokedex.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides bindings for Pokémon-related repositories and services.
 *
 * This setup ensures all components are injected with proper dependencies
 * and initialized as singletons within the application scope.
 */
@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    @Provides
    @Singleton
    fun provideLogger(): ILogger = Logger

    @Provides
    @Singleton
    fun providePokemonRepository(logger: ILogger): IPokemonRepository {
        return PokemonRepository(logger)
    }

    @Provides
    @Singleton
    fun providePokemonService(repository: IPokemonRepository): IPokemonService {
        return PokemonService(repository)
    }
}