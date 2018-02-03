package com.circles.circlesbot.model.store

import com.circles.circlesbot.model.api.QnAResponse

/**
 * Created by xyriz on 8/2/18.
 */

object ChatMessageProvider {
  var chatMessageList = mutableListOf<ChatMessageModel>()

  fun insertToDatabase(qnAResponse: QnAResponse){
    qnAResponse.answers.forEach {
      val chatMessageModel = ChatMessageModel(it.answer, Direction.INCOMING)
      insertToDatabase(chatMessageModel)
    }
  }

  fun insertToDatabase(chatMessageModel: ChatMessageModel){
      chatMessageList.add(chatMessageModel)
  }

}