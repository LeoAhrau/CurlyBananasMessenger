package com.example.curlybananasmessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomChatAdapter(private val context: Context, private var chatList: MutableList<String>) :
    RecyclerView.Adapter<CustomChatAdapter.ChatMessageHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewChatLayout: Int): ChatMessageHolder {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(
                if (viewChatLayout % 2 == 0) R.layout.sender_item_container else R.layout.receiver_item_container,
                parent, false
            )
            return ChatMessageHolder(view)
        }
        override fun onBindViewHolder(holder: ChatMessageHolder, position: Int) {
            val chatMessageText = chatList[position]
            holder.chatTextTextView.text = chatMessageText
        }

        override fun getItemCount(): Int {
            return chatList.size
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        inner class ChatMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val chatTextTextView: TextView = itemView.findViewById(R.id.tv_chat_container)
        }
}