package com.example.curlybananasmessenger

import android.widget.ArrayAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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
