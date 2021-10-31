package com.koby.blazepodskotlin.di

import android.content.Context
import androidx.room.Room
import com.koby.blazepodskotlin.data.local.AppDatabase
import com.koby.blazepodskotlin.data.remote.MyAppService
import com.koby.blazepodskotlin.repository.Repository
import com.koby.blazepodskotlin.repository.RepositoryInterface
import com.koby.blazepodskotlin.util.Constants.API_KEY
import com.koby.blazepodskotlin.util.Constants.APP_DATABASE
import com.koby.blazepodskotlin.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            APP_DATABASE
        )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideImdbService(
        retrofit: Retrofit
    ): MyAppService {
        return retrofit.create(MyAppService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request();
            val url = request.url.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build();
            val newRequest = request.newBuilder().url(url).build();
            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        okHttpLogger: HttpLoggingInterceptor,
        okHttpNetworkInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLogger)
            .addInterceptor(okHttpNetworkInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    /*
      @Singleton
      @Provides
      fun provideOkHttpNetworkInterceptor(): Interceptor {
          return Interceptor { chain ->
              val newRequest =
                  chain.request().newBuilder().addHeader(HEADER_API_KEY, API_KEY).build()
              chain.proceed(newRequest)
          }
    }*/

    @Provides
    fun provideRepository(
        appDatabase: AppDatabase,
        myAppService: MyAppService
    ) : RepositoryInterface{
        return Repository(appDatabase,myAppService)
    }
}
