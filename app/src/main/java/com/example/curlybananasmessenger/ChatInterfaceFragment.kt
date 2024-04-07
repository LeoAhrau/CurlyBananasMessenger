package com.example.curlybananasmessenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curlybananasmessenger.databinding.FragmentChatInterfaceBinding

class ChatInterfaceFragment : Fragment() {
    private lateinit var binding: FragmentChatInterfaceBinding
    private lateinit var messageField: EditText
    private lateinit var sendButton: Button
    private lateinit var chatList: RecyclerView
    private lateinit var adapter: CustomChatAdapter

    private val chatItemList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentChatInterfaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //------------- Testing --------------------
        messageField = binding.etChatMessage
        sendButton = binding.btnSendMessage
        chatList = binding.rvChat

        adapter = CustomChatAdapter(requireContext(), chatItemList)
        chatList.adapter = adapter
        chatList.layoutManager = LinearLayoutManager(requireContext())

        sendButton.setOnClickListener {
            val messageText = messageField.text.toString()
            if (messageText.isNotEmpty()) {
                chatItemList.add(messageText)
                adapter.notifyItemInserted(chatItemList.size - 1)
                //chatList.smoothScrollToPosition(chatItemList.size - 1)
                messageField.text.clear()
            }
        }
        //------------- Testing --------------------
    }
}