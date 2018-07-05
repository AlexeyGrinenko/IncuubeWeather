package com.alexg.incuubeweather.http

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModuleForForecast {

    val BASE_URL = "https://query.yahooapis.com/v1/public/"
    //    public final String API_KEY = "8551c026bbf22a4a386ebb5b87a5296b";


    @Provides
    fun provideClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder()
                    //                        .addQueryParameter(
                    //                        "format",
                    //                        "json"
                    //                )
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()
    }

    @Provides
    fun provideRetrofit(baseURL: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    fun provideApiService(): ForecastApiService {
        return provideRetrofit(BASE_URL, provideClient()).create(ForecastApiService::class.java)
    }

    companion object {

        fun convertLocationToQuery(location: String): String {
            return "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$location\")"
        }
    }

}
