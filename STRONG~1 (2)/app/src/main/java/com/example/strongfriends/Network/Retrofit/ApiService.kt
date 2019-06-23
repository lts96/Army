package com.example.strongfriends.Network.Retrofit

import com.example.strongfriends.Application.Activity.Datas.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    companion object Factory {
        private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            val b = OkHttpClient.Builder()
            b.addInterceptor(interceptor)
            return b.build()
        }

        private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return interceptor
        }

        fun create(): ApiService { //보낼곳의 url을 받아서 ApiService를 만들어서 리턴한다.
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient(provideLoggingInterceptor()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://221.155.56.120:8080/")
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }


    //@FormUrlEncoded
    @POST("main/sign_in")
    fun register(
        @Body body: register_body
    ): Observable<Result>

    //@FormUrlEncoded
    @PUT("main/log_in")
    fun login(
        @Body body: login_body
    ): Observable<Result>

    // @FormUrlEncoded
    @PUT("main/userlist/userQuery")
    fun periodicQuery(
        @Body body: Periodic_body
    ): Observable<Periodic_response>

    @FormUrlEncoded
    @PUT("main/enterRoom")
    fun enterRoom(
        @Field("userId") userid: String,
        @Field("groupPin") pin: Int
    ): Observable<EnterRoom_response>

    @FormUrlEncoded
    @PUT("main/groupId/violoation")
    fun violation(
        @Field("userId") userId:String,
        @Field("groupId")groupId:String,
        @Field("violationId") violationId:String
    )

}