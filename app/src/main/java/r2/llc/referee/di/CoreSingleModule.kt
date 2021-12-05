package r2.llc.referee.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import r2.llc.referee.repository.MainRepository
import r2.llc.referee.repository.impl.MainRepositoryImpl
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
interface CoreSingleModule {

    @[Binds Singleton]
    fun bindMainRepository(binder: MainRepositoryImpl): MainRepository
}