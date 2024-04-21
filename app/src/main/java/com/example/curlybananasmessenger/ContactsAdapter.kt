package com.example.curlybananasmessenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private var contacts: List<Contacts> = listOf()

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)

        fun bind(contact: Contacts) {
            nameTextView.text = contact.name
            emailTextView.text = contact.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    fun updateContacts(newContacts: List<Contacts>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}


//class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
//    private var contacts: List<Contacts> = emptyList()
//
//    fun setContacts(contacts: List<Contacts>) {
//        this.contacts = contacts
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val contact = contacts[position]
//        holder.nameTextView.text = contact.name
//        holder.emailTextView.text = contact.email
//    }
//
//    override fun getItemCount() = contacts.size
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
//        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
//    }
//}

//
//class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
//    private var contacts: List<Contacts> = listOf()
//
//    fun setContacts(contacts: List<Contacts>) {
//        this.contacts = contacts
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.nameTextView.text = contacts[position].name
//        holder.emailTextView.text = contacts[position].email
//    }
//
//    override fun getItemCount() = contacts.size
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nameTextView: TextView = itemView.findViewById(R.id.name)
//        val emailTextView: TextView = itemView.findViewById(R.id.email)
//    }
//}
