package com.example.curlybananasmessenger

import android.os.Bundle
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
    private lateinit var adapter: CustomChatAdapter

    private val chatItemList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //------------- Testing --------------------
        messageField = binding.etChatMessage
        sendButton = binding.btnSendMessage
        chatList = binding.lvChat

        adapter = CustomChatAdapter(requireContext(), chatItemList)
        chatList.adapter = adapter

        sendButton.setOnClickListener {
            val chatMessage = messageField.text.toString()
            if (chatMessage.isNotEmpty()) {
                chatItemList.add(chatMessage)
                adapter.updateText(chatItemList)
                messageField.text.clear()
            }
        }
        //------------- Testing --------------------
    }
}