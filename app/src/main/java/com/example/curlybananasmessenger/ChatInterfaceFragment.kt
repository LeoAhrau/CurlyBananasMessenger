package com.example.curlybananasmessenger

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.curlybananasmessenger.databinding.FragmentChatInterfaceBinding

class ChatInterfaceFragment : Fragment() {
    private lateinit var binding: FragmentChatInterfaceBinding
    private lateinit var messageField: EditText
    private lateinit var sendButton: Button
    private lateinit var chatList: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val itemList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Testing
        messageField = binding.etChatMessage
        sendButton = binding.btnSendMessage
        chatList = binding.lvChat

        adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, itemList)
        chatList.adapter = adapter

        sendButton.setOnClickListener {
            val chatMessage = messageField.text.toString()
            if (chatMessage.isNotEmpty()) {
                itemList.add(chatMessage)
                adapter.notifyDataSetChanged() // Change this.
                messageField.text.clear()
            }

        }

    }
}