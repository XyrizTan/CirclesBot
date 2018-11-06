package com.circles.circlesbot.model.api

import com.google.gson.annotations.SerializedName

/**
 * Created by xyriz on 8/2/18.
 */

data class QnARequest(@SerializedName("question") val question: String)