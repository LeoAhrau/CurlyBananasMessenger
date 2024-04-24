package com.example.curlybananasmessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.SenderItemContainerBinding

class CustomChatMessageAdapter(
    private val context: Context,
    private var chatList: List<ChatMessage>,
    private val currentUser: String,
    private val contactId: String
) :
    RecyclerView.Adapter<CustomChatMessageAdapter.ChatMessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageHolder {
        val layoutRes = if (chatList[viewType].senderId == currentUser) {
            R.layout.sender_item_container
        } else {
            R.layout.receiver_item_container
        }
        val view = LayoutInflater.from(context).inflate(layoutRes, parent, false)
        return ChatMessageHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessageHolder, position: Int) {
        val chatMessage = chatList[position]
        holder.bind(chatMessage)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ChatMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chatTextTextView: TextView = itemView.findViewById(R.id.tv_chat_container)

        fun bind(chatMessage: ChatMessage) {
            chatTextTextView.text = chatMessage.message
        }
    }
}