package com.circles.circlesbot.presenter

import com.circles.circlesbot.model.api.ChatbotApi
import com.circles.circlesbot.model.api.QnARequest
import com.circles.circlesbot.model.api.QnAResponse
import com.circles.circlesbot.model.store.ChatMessageModel
import com.circles.circlesbot.model.store.ChatMessageProvider
import com.circles.circlesbot.model.store.Direction
import com.circles.circlesbot.view.ChatView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by xyriz on 8/2/18.
 */

class ChatPresenter(val view: ChatView, val chatbotApi: ChatbotApi,
    val chatMessageProvider: ChatMessageProvider) {

  init {
    view.setPresenter(this)
  }

  val TAG = ChatPresenter::class.java.simpleName

  fun sendMessage(queryString: String) {
    chatMessageProvider.insertToDatabase(ChatMessageModel(queryString, Direction.OUTGOING))
    view.updateChatConversation(chatMessageProvider.chatMessageList)

    val call = chatbotApi.generateAnswer(
        request = QnARequest(question = queryString))

    val callback = object : Callback<QnAResponse> {
      override fun onFailure(call: Call<QnAResponse>, t: Throwable) {
        t.printStackTrace()
      }

      override fun onResponse(call: Call<QnAResponse>, response: Response<QnAResponse>) {
        if (response.isSuccessful) {
          response.body()?.let {
            chatMessageProvider.insertToDatabase(it)
            view.updateChatConversation(chatMessageProvider.chatMessageList)
          }
        } else {
          view.showError(response.errorBody().toString())
        }
      }
    }

    call.enqueue(callback)
  }
}

