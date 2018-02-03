package com.circles.circlesbot.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.circles.circlesbot.model.store.ChatMessageModel
import com.circles.circlesbot.model.store.Direction.INCOMING
import com.circles.circlesbot.model.store.Direction.OUTGOING
import com.circles.circlesbot.R.layout
import com.circles.circlesbot.view.ChatAdapter.VH
import kotlinx.android.synthetic.main.cb_chat_fragment_listitem_speechbubble_incoming.view.*
import kotlinx.android.synthetic.main.cb_chat_fragment_listitem_speechbubble_outgoing.view.*
/**
 * Created by xyriz on 3/2/18.
 */

class ChatAdapter(var chatMessageList: List<ChatMessageModel>) : RecyclerView.Adapter<VH>() {

  fun updateList(list : List<ChatMessageModel>) {
    chatMessageList = list
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH? {
    return when (viewType) {
      OUTGOING.viewType -> {
        OutgoingVH(LayoutInflater.from(parent?.context).inflate(
            layout.cb_chat_fragment_listitem_speechbubble_outgoing,
            parent, false))
      }
      INCOMING.viewType -> {
        IncomingVH(LayoutInflater.from(parent?.context).inflate(
            layout.cb_chat_fragment_listitem_speechbubble_incoming,
            parent, false))
      }
      else -> {
        null
      }
    }
  }

  override fun getItemCount(): Int {
    return chatMessageList.size
  }

  override fun onBindViewHolder(holder: VH?, position: Int) {
    holder?.bind(chatMessageList[position])
  }

  override fun getItemViewType(position: Int): Int {
    return chatMessageList[position].direction.viewType
  }

  abstract inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: ChatMessageModel)
  }

  inner class IncomingVH(itemView: View) : VH(itemView) {
    override fun bind (model : ChatMessageModel) {
      itemView.cb_chat_fragment_listitem_speechbubble_incoming_textview.text = model.message
    }
  }

  inner class OutgoingVH(itemView: View) : VH(itemView) {
    override fun bind(model : ChatMessageModel) {
      itemView.cb_chat_fragment_listitem_speechbubble_outgoing_textview.text = model.message
    }
  }
}