package com.example.starwarsapp.domain.use_cases

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.starwarsapp.domain.model.Starship
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import com.example.starwarsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SearchForStarshipsUseCase(
    private val repository: StarWarsRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend operator fun invoke(text: String): Flow<Resource<List<Starship>>> = flow {
        try {
            emit(Resource.Loading())
            var page = 1
            val list = mutableListOf<Starship>()
            while (true) {
                val starships = repository.getStarships(text, page)
                list.addAll(starships.results.map { it.toStarship() })
                if (starships.next == null) {
                    break
                }
                page++
            }
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(Resource.Error("HttpException"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your connection"))
        }
    }
}