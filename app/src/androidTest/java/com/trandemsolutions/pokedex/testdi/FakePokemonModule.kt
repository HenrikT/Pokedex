package com.trandemsolutions.pokedex.testdi

import com.trandemsolutions.pokedex.data.FakePokemonRepository
import com.trandemsolutions.pokedex.data.FakePokemonService
import com.trandemsolutions.pokedex.data.IPokemonRepository
import com.trandemsolutions.pokedex.data.IPokemonService
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [com.trandemsolutions.pokedex.di.PokemonModule::class]
)
abstract class FakePokemonModule {

    @Binds
    @Singleton
    abstract fun bindFakeRepository(impl: FakePokemonRepository): IPokemonRepository

    @Binds
    @Singleton
    abstract fun bindFakeService(impl: FakePokemonService): IPokemonService
}