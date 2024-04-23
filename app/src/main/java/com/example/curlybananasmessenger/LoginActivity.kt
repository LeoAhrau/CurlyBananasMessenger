package com.example.curlybananasmessenger

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.curlybananasmessenger.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var fbStorage: FirebaseStorage
    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler? = null
    private var soundRunnable: Runnable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFallingBananas()

//        binding.buttonToRoom.setOnClickListener {
//            val intent = Intent(this, ChatActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        binding.buttonShowContacts.setOnClickListener {
//            val intent = Intent(this, ContactsActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            authenticateUser(username, password)
        }


    }

    fun authenticateUser(username: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches() || password.isEmpty()) {
            Toast.makeText(
                this, "Both or one Field is incorrect/empty",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            login(username, password)
        }

    }

    fun login(username: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                val intent = Intent(this, ContactActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { error ->
                Toast.makeText(
                    this, "Thy credentials tis not correct",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun addFallingBananas() {
        val container =
            findViewById<FrameLayout>(R.id.backgroundContainer) // Se till att detta är en FrameLayout i din layoutfil

        for (i in 1..1) {
            val banana = ImageView(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).also {
                    it.topMargin = -600 // Starta ovanför skärmen
                    it.leftMargin =
                        (0..container.width).random() // Starta på slumpmässiga horisontella positioner
                }
                setImageResource(R.drawable.cute_banana_hands)
            }
            container.addView(banana)
            playSound(R.raw.cute_character_wee)
            val anim = AnimationUtils.loadAnimation(this, R.anim.fall_down)
            anim.startOffset =
                (0..1000).random().toLong() // Slumpmässig startfördröjning för osynkronisering
            banana.startAnimation(anim)
        }
    }


    private fun playSound(soundResourceId: Int) {
        handler = Handler(Looper.getMainLooper())
        soundRunnable = Runnable {
            // Release any previous MediaPlayer instance
            mediaPlayer?.release() // Släpp alla tidigare MediaPlayer-instanser om de existerar


            mediaPlayer = MediaPlayer.create(this, soundResourceId).apply {   // Skapa en ny MediaPlayer-instans och konfigurera den
                start()
                setOnCompletionListener { mp ->  // Ställ in en lyssnare som kallas när uppspelningen är klar
                    // Stop och frigör MediaPlayer när ljudet är klart
                    mp.stop()
                    mp.release()

                    // Nullställ referensen så att vi vet att den inte längre är initialiserad
                    mediaPlayer = null
                }

                setOnErrorListener { mp, what, extra ->
                    mp.release()
                    mediaPlayer = null
                    Log.e("MediaPlayer", "Error occurred during playback")
                    true
                }
            }
        }
        handler?.postDelayed(soundRunnable!!, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundRunnable?.let { handler?.removeCallbacks(it) } // Ta bort eventuella schemalagda åtgärder
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }
}