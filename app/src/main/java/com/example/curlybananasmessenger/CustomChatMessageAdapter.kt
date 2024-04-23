package com.example.curlybananasmessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.SenderItemContainerBinding
import com.google.firebase.auth.FirebaseAuth

class CustomChatMessageAdapter(val context: Context, var chatList: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val layoutRes = if (viewType == ITEM_SENT) R.layout.sender_item_container else R.layout.receiver_item_container
        val view = inflater.inflate(layoutRes, parent, false)
        return when (viewType) {
            ITEM_SENT -> SentViewHolder(view)
            else -> ReceiveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = chatList[position]
        when (holder) {
            is SentViewHolder -> holder.sentMessage.text = chatMessage.message
            is ReceiveViewHolder -> holder.receiveMessage.text = chatMessage.message
        }
    }

    override fun getItemCount(): Int = chatList.size

    override fun getItemViewType(position: Int): Int {
        val chatMessage = chatList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid == chatMessage.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.tv_chat_container)
    }

    inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.tv_chat_container)
    }
}
