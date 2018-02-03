package com.circles.circlesbot.model.store

/**
 * Created by xyriz on 3/2/18.
 */

data class ChatMessageModel(val message: String, val direction: Direction)

enum class Direction(val viewType: Int) {
  INCOMING(1),
  OUTGOING(2)
}