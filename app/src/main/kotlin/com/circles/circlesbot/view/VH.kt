package com.circles.circlesbot.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.circles.circlesbot.model.store.ChatMessageModel
import kotlinx.android.synthetic.main.cb_chat_fragment_listitem_speechbubble_incoming.view.*
import kotlinx.android.synthetic.main.cb_chat_fragment_listitem_speechbubble_outgoing.view.*

sealed class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: ChatMessageModel)

    class IncomingVH(itemView: View) : VH(itemView) {
        override fun bind(model: ChatMessageModel) {
            itemView.cb_chat_fragment_listitem_speechbubble_incoming_textview.text = model.message
        }
    }

    class OutgoingVH(itemView: View) : VH(itemView) {
        override fun bind(model: ChatMessageModel) {
            itemView.cb_chat_fragment_listitem_speechbubble_outgoing_textview.text = model.message
        }
    }

}