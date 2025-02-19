

package com.example.curlybananasmessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class CustomChatMessageAdapter(
    private val context: Context,
    private var chatList: List<ChatMessage>,
    private val currentUser: String
) :
    RecyclerView.Adapter<CustomChatMessageAdapter.ChatMessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewChatLayout: Int): ChatMessageHolder {
        val layoutRes = if (chatList[viewChatLayout].senderId == currentUser) R.layout.sender_item_container else R.layout.receiver_item_container
        val view = LayoutInflater.from(context).inflate(layoutRes, parent, false)
        return ChatMessageHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessageHolder, position: Int) {
        val chatMessageText = chatList[position]
        holder.chatTextTextView.text = chatMessageText.message
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

