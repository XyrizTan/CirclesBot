package com.circles.circlesbot.model.api

/**
 * Created by xyriz on 8/2/18.
 */
data class QnAResponse(val answers: List<Answer>) {
  data class Answer(val answer: String,
      val questions: List<String>,
      val score: Double)
}