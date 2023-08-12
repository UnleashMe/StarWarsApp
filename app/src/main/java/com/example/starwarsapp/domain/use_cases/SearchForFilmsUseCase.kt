package com.example.starwarsapp.domain.use_cases

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.starwarsapp.domain.model.Film
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import com.example.starwarsapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SearchForFilmsUseCase(
    private val repository: StarWarsRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

    suspend operator fun invoke(ids: Set<Int>, coroutineScope: CoroutineScope): Flow<Resource<Set<Film>>> = flow {
        try {
            emit(Resource.Loading())
            val set = mutableSetOf<Film>()
            val deferredList = ids.map { id ->
                coroutineScope.async(Dispatchers.IO) {
                    repository.getFilmById(id)?.toFilm()
                }
            }
            deferredList.awaitAll().forEach { film ->
                film?.let { set.add(it) }
            }
            emit(Resource.Success(set))
        } catch (e: IOException) {
            emit(Resource.Error("Check your connection"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http connection"))
        }
    }

    /*                                                                                               Tests won't run with this function for some reason
    suspend operator fun invoke(
        ids: Set<Int>,
        coroutineScope: CoroutineScope
    ): Flow<Resource<Set<Film>>> = flow {*//*
        val channel = Channel<Resource<Set<Film>>>()
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch {
                if (throwable is IOException) {
                    channel.send(Resource.Error("Check your connection"))
                } else {
                    channel.send(Resource.Error("Some error occurred"))
                }
            }
        }
        coroutineScope.launch(exceptionHandler) {
            channel.send(Resource.Loading())
            val set = mutableSetOf<Film>()
            val deferredList = ids.map { id ->
                async(Dispatchers.IO) {
                    repository.getFilmById(id)?.toFilm()
                }
            }
            try {
                deferredList.awaitAll().forEach { film ->
                    film?.let { set.add(it) }
                }
                channel.send(Resource.Success(set))
            } catch (e: Exception) {
                channel.send(Resource.Error("Some error occurred"))
            }
        }
        return channel.receiveAsFlow()*//*
    }*/
}