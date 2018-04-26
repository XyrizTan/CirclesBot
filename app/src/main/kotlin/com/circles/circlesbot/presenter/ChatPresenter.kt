package com.circles.circlesbot.presenter

import com.circles.circlesbot.model.store.ChatMessageStore
import com.circles.circlesbot.view.ChatView

/**
 * Created by xyriz on 8/2/18.
 */

class ChatPresenter(val view: ChatView, val chatMessageProvider: ChatMessageStore) {

    init {
        view.setPresenter(this)
    }

    val TAG = ChatPresenter::class.java.simpleName

    fun sendMessage(queryString: String) {
        chatMessageProvider.sendChatMessage(queryString,
                { it -> view.updateChatConversation(it) },
                { it -> view.showError(it) });
    }
}

