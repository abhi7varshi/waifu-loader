package com.example.waifuloader.data.di

import com.example.waifuloader.data.WaifuRepository
import com.example.waifuloader.data.repository.RetrofitWaifuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsWaifuRepository(repo: RetrofitWaifuRepository): WaifuRepository
}