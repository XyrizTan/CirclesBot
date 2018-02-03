package com.circles.circlesbot.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.circles.circlesbot.model.store.ChatMessageModel
import com.circles.circlesbot.R.layout
import com.circles.circlesbot.model.api.ApiProvider
import com.circles.circlesbot.model.store.ChatMessageProvider
import com.circles.circlesbot.presenter.ChatPresenter
import kotlinx.android.synthetic.main.cb_chat_fragment.view.*
import java.lang.ref.WeakReference

class ChatFragment : Fragment(), ChatView {

  lateinit var mChatPresenter: ChatPresenter

  val TAG = ChatFragment::class.java.simpleName

  override fun setPresenter(chatPresenter: ChatPresenter) {
    this.mChatPresenter = chatPresenter
  }

  override fun updateChatConversation(list: List<ChatMessageModel>) {
    view?.let {
      (it.cb_chat_fragment_recyclerview?.adapter as ChatAdapter).updateList(list)
    }
  }

  override fun showError(errorMessage: String) {
    view?.let {
      Snackbar.make(it, "Error: $errorMessage", Snackbar.LENGTH_LONG)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WeakReference(ChatPresenter(this, ApiProvider.chatbotApi, ChatMessageProvider))
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(layout.cb_chat_fragment, container, false)
  }


  override fun onViewCreated(fragmentView: View?, savedInstanceState: Bundle?) {
    // set up views here
    fragmentView?.cb_chat_fragment_recyclerview?.layoutManager = LinearLayoutManager(context,
        LinearLayoutManager.VERTICAL, false)

    fragmentView?.cb_chat_fragment_recyclerview?.adapter = ChatAdapter(emptyList())

    fragmentView?.cb_chat_fragment_input_edittext?.setOnEditorActionListener { textView, actionId, keyEvent ->
      if (actionId == EditorInfo.IME_ACTION_SEND) {
        sendMessage(textView.text.toString())
        true
      }
      false
    }
    fragmentView?.cb_chat_fragment_send_button?.setOnClickListener {
      sendMessage(fragmentView.cb_chat_fragment_input_edittext.text.toString())
    }


  }

  private fun sendMessage(queryString: String) {
    view?.let {
      if (queryString.isNotBlank()) {
        it.cb_chat_fragment_input_edittext.text.clear()
        mChatPresenter.sendMessage(queryString)
        it.cb_chat_fragment_recyclerview.layoutManager.scrollToPosition(it.cb_chat_fragment_recyclerview.adapter.itemCount-1)
      }
    }
  }

}