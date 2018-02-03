package com.circles.circlesbot.view

import com.circles.circlesbot.model.store.ChatMessageModel
import com.circles.circlesbot.presenter.ChatPresenter

/**
 * Created by xyriz on 8/2/18.
 */

interface ChatView {

  fun updateChatConversation(list : List<ChatMessageModel>)

  fun showError(errorMessage: String)

  fun setPresenter(chatPresenter: ChatPresenter)
}