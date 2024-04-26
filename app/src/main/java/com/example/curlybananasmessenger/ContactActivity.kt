package com.example.curlybananasmessenger


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import java.util.UUID

class ContactActivity : BaseActivity() {

    lateinit var binding: ActivityContactBinding
    lateinit var customAdapter: CustomContactsListAdapter
    lateinit var contactDao: ContactDao
    private lateinit var allContacts: ArrayList<Contact>
    lateinit var mainView: ConstraintLayout
    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler? = null
    private var soundRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView = binding.mainLayout

        // Initialize custom adapter for ListView
        customAdapter = CustomContactsListAdapter(this, ArrayList())
        binding.lvContacts.adapter = customAdapter

        contactDao = ContactDao(this)

        allContacts = ArrayList()

        // TextWatcher for filtering contacts as user types in search field
        binding.edSearchContact.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter contacts based on the entered text
                filterContacts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No implementation needed
            }
        })

        binding.btnAddContact.setOnClickListener {
            addContact()
            dancingBanana()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    mainView.visibility = View.VISIBLE
                } else {
                    finish()
                }
            }
        })

        // Item click listener to open chat activity when a contact is clicked
        binding.lvContacts.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                val intent = Intent(this, ChatInterfaceActivity::class.java)
                intent.putExtra("contactId", selectedContact.contactId)
                intent.putExtra("contactName", selectedContact.contactName)
                startActivity(intent)
            }

        // Item long click listener to delete a contact
        binding.lvContacts.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                contactDao.deleteContact(selectedContact)
                true
            }
    }

    private fun addContact() {
        try {
            val contactName = binding.etContactName.text.toString()
            val contactEmail = binding.etContactEmail.text.toString()

            // Create a new contact object with UUID as contact ID
            val contact = Contact(UUID.randomUUID().toString(), contactName, contactEmail)
            // Add the contact using ContactDao
            contactDao.addContact(this, contact)

            binding.etContactName.text.clear()
            binding.etContactEmail.text.clear()

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    // Function to filter contacts based on search query
    private fun filterContacts(query: String) {
        val filteredContacts = ArrayList<Contact>()

        for (contact in allContacts) {
            if (contact.contactName?.contains(query, ignoreCase = true) == true ||
                contact.contactEmail?.contains(query, ignoreCase = true) == true
            ) {
                filteredContacts.add(contact)
            }
        }

        // Update adapter with filtered contacts
        customAdapter.clear()
        customAdapter.addAll(filteredContacts)
        customAdapter.notifyDataSetChanged()
    }

    fun showContacts(contactList: ArrayList<Contact>) {
        // Sort contacts by name
        val sortedContacts = contactList.sortedWith(compareBy { it.contactName?.lowercase() })

        //Update allContacts list
        allContacts.clear()
        allContacts.addAll(sortedContacts)

        // Filter contacts based on current search query
        filterContacts(binding.edSearchContact.text.toString())
    }
    fun playSound() {
        handler = Handler(Looper.getMainLooper())
        soundRunnable = Runnable {

            mediaPlayer?.release()  // Release any previous MediaPlayer instances if they exist


            // Create a new MediaPlayer instance and configure it to play the 'cute_character_wee_two' sound
            mediaPlayer = MediaPlayer.create(this, R.raw.dudududu_tunes_girl).apply {
                start()
                setOnCompletionListener { mp ->
                    // Stop and release the MediaPlayer when the sound is done playing
                    mp.stop()
                    mp.release()


                    // Reset the reference so we know it's no longer initialized
                    mediaPlayer = null
                }


                setOnErrorListener { mp, what, extra ->
                    // Handle errors during playback
                    mp.release()
                    mediaPlayer = null
                    Log.e("MediaPlayer", "Error occurred during playback")
                    true
                }
            }
        }
        handler?.post(soundRunnable!!)
    }


    override fun onDestroy() {
        super.onDestroy()
        // Ensure all scheduled actions are removed to prevent memory leaks
        soundRunnable?.let { handler?.removeCallbacks(it) }
        // Clean up the MediaPlayer instance
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }
    private fun dancingBanana() {
        val container =
            findViewById<FrameLayout>(R.id.backgroundContainer)

        for (i in 1..1) {
            val banana = ImageView(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).also {
                    it.topMargin = 100
                    it.leftMargin = 150

                }
                setImageResource(R.drawable.cute_banana_hands)
            }
            container.addView(banana)
            playSound()
            val anim = AnimationUtils.loadAnimation(this, R.anim.dancing_banana)
            anim.startOffset = -100

            banana.startAnimation(anim)
        }
    }

}


