package com.circles.circlesbot.model.store

import com.circles.circlesbot.model.api.ChatbotApi
import com.circles.circlesbot.model.api.QnARequest
import com.circles.circlesbot.model.api.QnAResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by xyriz on 8/2/18.
 */

class ChatMessageStore(val chatbotApi: ChatbotApi) {
  var chatMessageList = mutableListOf<ChatMessageModel>()

  fun sendChatMessage(queryString: String, onSuccess: (List<ChatMessageModel>) -> Unit, onFailure: (String) -> Unit){
    insertToDatabase(ChatMessageModel(queryString, Direction.OUTGOING))
    onSuccess(chatMessageList)

    val call = chatbotApi.generateAnswer(
            request = QnARequest(question = queryString))

    val callback = object : Callback<QnAResponse> {
      override fun onFailure(call: Call<QnAResponse>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<QnAResponse>, response: Response<QnAResponse>) {
        if (response.isSuccessful) {
          response.body()?.let {
            insertResponseToDatabase(it)
            onSuccess(chatMessageList)
          }
        } else {
          // Mark message as unsent
          onFailure(response.errorBody().toString())
        }

        // notify presenter here
      }
    }

    call.enqueue(callback)
  }

  private fun insertResponseToDatabase(qnAResponse: QnAResponse){
    qnAResponse.answers.forEach {
      val chatMessageModel = ChatMessageModel(it.answer, Direction.INCOMING)
      insertToDatabase(chatMessageModel)
    }
  }

  private fun insertToDatabase(chatMessageModel: ChatMessageModel){
      chatMessageList.add(chatMessageModel)
  }

}