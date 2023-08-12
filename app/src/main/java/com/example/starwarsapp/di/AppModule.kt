package com.example.starwarsapp.di

import android.content.Context
import androidx.room.Room
import com.example.starwarsapp.data.database.StarWarsDao
import com.example.starwarsapp.data.database.StarWarsDatabase
import com.example.starwarsapp.data.network.StarWarsService
import com.example.starwarsapp.data.repositories.StarWarsRepositoryImpl
import com.example.starwarsapp.domain.repositories.StarWarsRepository
import com.example.starwarsapp.domain.use_cases.DatabaseUseCases
import com.example.starwarsapp.domain.use_cases.FaveCharacterUseCase
import com.example.starwarsapp.domain.use_cases.FavePlanetUseCase
import com.example.starwarsapp.domain.use_cases.FaveStarshipUseCase
import com.example.starwarsapp.domain.use_cases.GetFavouriteCharactersUseCase
import com.example.starwarsapp.domain.use_cases.GetFavouritePlanetsUseCase
import com.example.starwarsapp.domain.use_cases.GetFavouriteStarshipsUseCase
import com.example.starwarsapp.domain.use_cases.NetworkUseCases
import com.example.starwarsapp.domain.use_cases.SearchForCharactersUseCase
import com.example.starwarsapp.domain.use_cases.SearchForFilmsUseCase
import com.example.starwarsapp.domain.use_cases.SearchForPlanetsUseCase
import com.example.starwarsapp.domain.use_cases.SearchForStarshipsUseCase
import com.example.starwarsapp.domain.use_cases.UnfaveCharacterUseCase
import com.example.starwarsapp.domain.use_cases.UnfavePlanetUseCase
import com.example.starwarsapp.domain.use_cases.UnfaveStarshipUseCase
import com.example.starwarsapp.util.Constants.BASE_URL
import com.example.starwarsapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesStarWarsDao(@ApplicationContext context: Context): StarWarsDao {
        return Room.databaseBuilder(context, StarWarsDatabase::class.java, DATABASE_NAME).build()
            .getDao()
    }

    @Provides
    @Singleton
    fun providesStarWarsService(): StarWarsService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(StarWarsService::class.java)
    }

    @Provides
    @Singleton
    fun providesStarWarsRepository(
        starWarsService: StarWarsService,
        starWarsDao: StarWarsDao
    ): StarWarsRepository {
        return StarWarsRepositoryImpl(starWarsService, starWarsDao)
    }

    @Provides
    @Singleton
    fun providesNetworkUseCases(repository: StarWarsRepository): NetworkUseCases {
        return NetworkUseCases(
            SearchForCharactersUseCase(repository),
            SearchForStarshipsUseCase(repository),
            SearchForPlanetsUseCase(repository),
            SearchForFilmsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesDatabaseUseCases(repository: StarWarsRepository): DatabaseUseCases {
        return DatabaseUseCases(
            FaveCharacterUseCase(repository),
            UnfaveCharacterUseCase(repository),
            FavePlanetUseCase(repository),
            UnfavePlanetUseCase(repository),
            FaveStarshipUseCase(repository),
            UnfaveStarshipUseCase(repository),
            GetFavouriteCharactersUseCase(repository),
            GetFavouriteStarshipsUseCase(repository),
            GetFavouritePlanetsUseCase(repository)
        )
    }
}