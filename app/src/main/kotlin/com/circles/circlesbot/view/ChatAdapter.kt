package com.circles.circlesbot.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.circles.circlesbot.R.layout
import com.circles.circlesbot.model.store.ChatMessageModel
import com.circles.circlesbot.model.store.Direction.INCOMING
import com.circles.circlesbot.model.store.Direction.OUTGOING

/**
 * Created by xyriz on 3/2/18.
 */

class ChatAdapter(var chatMessageList: List<ChatMessageModel>) : ListAdapter<ChatMessageModel, VH>(DiffCallback.INSTANCE) {
    object DiffCallback {
        val INSTANCE = object : DiffUtil.ItemCallback<ChatMessageModel>() {
            override fun areItemsTheSame(oldItem: ChatMessageModel, newItem: ChatMessageModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatMessageModel, newItem: ChatMessageModel): Boolean {
                return oldItem == newItem
            }
        }
    }

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

    override fun getItemCount(): Int {
        return chatMessageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return chatMessageList[position].direction.viewType
    }

}