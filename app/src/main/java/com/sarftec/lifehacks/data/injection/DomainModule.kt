package com.sarftec.lifehacks.data.injection

import com.sarftec.lifehacks.data.repository.BookmarkRepositoryImpl
import com.sarftec.lifehacks.data.repository.HackRepositoryImpl
import com.sarftec.lifehacks.data.repository.ImageRepositoryImpl
import com.sarftec.lifehacks.domain.repository.BookmarkRepository
import com.sarftec.lifehacks.domain.repository.HackRepository
import com.sarftec.lifehacks.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {

    @Binds
    fun hackRepository(repository: HackRepositoryImpl) : HackRepository

    @Binds
    fun imageRepository(repository: ImageRepositoryImpl) : ImageRepository

    @Binds
    fun bookmarkRepository(repository: BookmarkRepositoryImpl) : BookmarkRepository
}