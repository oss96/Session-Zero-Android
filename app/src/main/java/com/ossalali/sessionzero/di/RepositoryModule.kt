package com.ossalali.sessionzero.di

import com.ossalali.sessionzero.data.repository.CharacterRepository
import com.ossalali.sessionzero.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl,
    ): CharacterRepository
}
