package com.circles.circlesbot.model.api

import com.google.gson.annotations.SerializedName

/**
 * Created by xyriz on 8/2/18.
 */
data class QnAResponse(@SerializedName("answers") val answers: List<Answer>) {
    data class Answer(@SerializedName("answer") val answer: String,
                      @SerializedName("questions") val questions: List<String>,
                      @SerializedName("score") val score: Double)
}