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
import com.circles.circlesbot.model.store.ChatMessageStore
import com.circles.circlesbot.presenter.ChatPresenter
import kotlinx.android.synthetic.main.cb_chat_fragment.view.*
import java.lang.ref.WeakReference

class ChatFragment : Fragment(), ChatView {

  lateinit var mChatPresenter: ChatPresenter

  val TAG = ChatFragment::class.java.simpleName

  override fun setPresenter(chatPresenter: ChatPresenter) {
    this.mChatPresenter = chatPresenter
  }

  private fun isLastMessageVisible() : Boolean{
    view?.let {
      val layoutManager = it.cb_chat_fragment_recyclerview.layoutManager as LinearLayoutManager
      val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
      val listSize = it.cb_chat_fragment_recyclerview.adapter.itemCount;
      if(listSize-2 <= lastVisibleItemPosition){
        return true;
      }
    }
    return false;
  }

  override fun updateChatConversation(list: List<ChatMessageModel>) {
    view?.let {
      (it.cb_chat_fragment_recyclerview?.adapter as ChatAdapter).updateList(list)
      if(isLastMessageVisible()) {
        scrollToPosition(it.cb_chat_fragment_recyclerview.adapter.itemCount - 1)
      }
    }
  }

  override fun showError(errorMessage: String) {
    view?.let {
      Snackbar.make(it, "Error: $errorMessage", Snackbar.LENGTH_LONG)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WeakReference(ChatPresenter(this, ChatMessageStore(ApiProvider.chatbotApi)))
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

  override fun scrollToPosition(position: Int) {
    view?.let {
      if (0 <= position && position < it.cb_chat_fragment_recyclerview.adapter.itemCount) {
        it.cb_chat_fragment_recyclerview.layoutManager.scrollToPosition(position)
      }
    }
  }

  private fun sendMessage(queryString: String) {
    view?.let {
      if (queryString.isNotBlank()) {
        it.cb_chat_fragment_input_edittext.text.clear()
        mChatPresenter.sendMessage(queryString)
        scrollToPosition(it.cb_chat_fragment_recyclerview.adapter.itemCount-1)
      }
    }
  }

}