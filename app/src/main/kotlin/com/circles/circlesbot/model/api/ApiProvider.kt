package com.circles.circlesbot.model.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level


/**
 * Created by xyriz on 8/2/18.
 */

object ApiProvider {
  val logging = HttpLoggingInterceptor().apply {
    level = Level.BODY
  }

  val httpClient : OkHttpClient = OkHttpClient.Builder().run {
    addInterceptor(logging)
    build()
  }

  val gson = GsonBuilder()
      .setLenient()
      .create()

  val chatbotApi= Retrofit.Builder()
      .baseUrl("https://westus.api.cognitive.microsoft.com/qnamaker/")
      .addConverterFactory(GsonConverterFactory.create(
          gson))
      .client(httpClient)
      .build()
      .create(ChatbotApi::class.java)
}