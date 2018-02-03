package com.circles.circlesbot.model.api

import com.circles.circlesbot.BuildConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by xyriz on 7/2/18.
 */

interface ChatbotApi {

  @POST("v2.0/knowledgebases/${BuildConfig.KNOWLEDGEBASE_KEY}/generateAnswer")
  fun generateAnswer(
      @Header("Ocp-Apim-Subscription-Key") subscriptionKey: String = BuildConfig.SUBSCRIPTION_KEY,
      @Body request : QnARequest): Call<QnAResponse>
}