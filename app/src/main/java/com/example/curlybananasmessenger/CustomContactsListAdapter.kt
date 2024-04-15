package com.example.curlybananasmessenger

import android.widget.ArrayAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import com.example.curlybananasmessenger.databinding.CustomContactsListAdapterBinding

class CustomContactsListAdapter(context: Context, contacts: List<Contact>) :
    ArrayAdapter<Contact>(context, 0, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_contacts, parent, false)
        }

        val contact = getItem(position)

        val cn = view!!.findViewById<TextView>(R.id.tv_contacts_name)
        cn.text = contact?.contactName

        val em = view.findViewById<TextView>(R.id.tv_contacts_email)
        em.text = contact?.contactEmail

        return view
    }
}


//class CustomContactsListAdapter(context: Context, contacts: List<Contact>) :
//    ArrayAdapter<Contact>(context, 0, contacts) {
//
//    lateinit var binding: CustomContactsListAdapterBinding
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
////        lateinit var binding: CustomContactsListAdapterBinding
//
//        val view: View
//
//        if (convertView == null) {
//            binding = CustomContactsListAdapterBinding.inflate(LayoutInflater(.from(context), parent, false)
//            view = binding.root
//            view.tag = binding
//        } else {
//            view = convertView
//            binding = view.tag as CustomContactsListAdapterBinding
//        }
//
//        val contact = getItem(position)
//
//        binding.tvContactsName.text = "Name: ${contact?.contactName}"
//        binding.tvContactsEmail.text = "e-mail: ${contact?.contactEmail}"
//
//        return view
//    }
//}