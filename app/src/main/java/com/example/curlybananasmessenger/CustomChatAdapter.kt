package com.example.curlybananasmessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomChatAdapter(context: Context, private var chatList: List<String>) :
    ArrayAdapter<String>(context, R.layout.chat_item_container, chatList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var chatItemView = convertView
        if (chatItemView == null) {
            val inflater = LayoutInflater.from(context)
            chatItemView = inflater.inflate(R.layout.chat_item_container, parent, false)
        }
        val textView = chatItemView!!.findViewById<TextView>(R.id.tv_chat_container)
        textView.text = chatList[position]

        return chatItemView
    }

    fun updateText(newText: List<String>) {
        chatList = newText
        notifyDataSetChanged()
    }
}