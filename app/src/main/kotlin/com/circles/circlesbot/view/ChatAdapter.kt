package com.circles.circlesbot.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.circles.circlesbot.R.layout
import com.circles.circlesbot.model.store.ChatMessageModel
import com.circles.circlesbot.model.store.Direction.INCOMING
import com.circles.circlesbot.model.store.Direction.OUTGOING

/**
 * Created by xyriz on 3/2/18.
 */

class ChatAdapter(var chatMessageList: List<ChatMessageModel>) : RecyclerView.Adapter<VH>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return when (viewType) {
      OUTGOING.viewType -> {
        VH.OutgoingVH(LayoutInflater.from(parent.context).inflate(
                layout.cb_chat_fragment_listitem_speechbubble_outgoing,
                parent, false))
      }
      INCOMING.viewType -> {
        VH.IncomingVH(LayoutInflater.from(parent.context).inflate(
                layout.cb_chat_fragment_listitem_speechbubble_incoming,
                parent, false))
      }
      else -> {
        // Change to empty view
        VH.IncomingVH(LayoutInflater.from(parent.context).inflate(
                layout.cb_chat_fragment_listitem_speechbubble_incoming,
                parent, false))
      }
    }
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.bind(chatMessageList[position])
  }

  fun updateList(list : List<ChatMessageModel>) {
    chatMessageList = list
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int {
    return chatMessageList.size
  }

  override fun getItemViewType(position: Int): Int {
    return chatMessageList[position].direction.viewType
  }

}