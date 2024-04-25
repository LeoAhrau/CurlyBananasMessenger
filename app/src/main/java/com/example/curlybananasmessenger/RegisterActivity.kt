package com.example.curlybananasmessenger

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue


class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDB: FirebaseDatabase
    val userDao = UserDao()
    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler? = null
    private var soundRunnable: Runnable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateBanana()

        binding.btnRegister.setOnClickListener {

            registerUser()
            navigateBackToLogin()
        }
    }

    fun registerUser() {
        try {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseDB = FirebaseDatabase.getInstance()
            val nickname = binding.etNickname.text.toString()
            val username = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()


            if (nickname.isEmpty()) {
                Toast.makeText(this, "Enter thy title", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                Toast.makeText(this, "Enter thy name ", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Enter thee secret word", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(username, password)
                    .addOnSuccessListener { authResult ->
                        val user = authResult.user
                        if (user != null) {
                            val userUid = user.uid
                            val user = User(userUid, nickname, username, password)
                            val timestamp = ServerValue.TIMESTAMP

                            // Add date of joining to user object
                            user.dateOfJoin = timestamp
                            userDao.registerUser(user)
                            Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "User registration failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("FAILURE", "User registration failed: ${e.message}")
                        Toast.makeText(this, "User registration failed", Toast.LENGTH_SHORT).show()
                    }
            }
        } catch (e: Exception) {
            Log.e("FAILURE", e.message.toString())
        }
    }
    private fun navigateBackToLogin() {
        // Creating intent to navigate back to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        // Optionally add flags to clear task or other navigation flags if needed
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()  // Call finish to destroy this activity and return to LoginActivity
    }

    fun animateBanana() {

        val bananaImage = findViewById<ImageView>(R.id.ivCuteBanana)
        bananaImage.visibility = View.VISIBLE  // Gör bananen synlig innan animationen startar

        val upAndAwayAnimation = AnimationUtils.loadAnimation(this, R.anim.jump_up)
        bananaImage.startAnimation(upAndAwayAnimation)


        upAndAwayAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                playSound()  // Play sound when the animation starts
            }


            override fun onAnimationEnd(animation: Animation) {
                // Gör bananen osynlig när animationen är klar
                bananaImage.visibility = View.INVISIBLE
            }


            override fun onAnimationRepeat(animation: Animation) {
                // Kod som körs om animationen upprepas (ej nödvändigt här)
            }
        })
    }


    fun playSound() {
        handler = Handler(Looper.getMainLooper())
        soundRunnable = Runnable {
            // Release any previous MediaPlayer instance
            mediaPlayer?.release()  // Release any previous MediaPlayer instances if they exist


            // Create a new MediaPlayer instance and configure it to play the 'cute_character_wee_two' sound
            mediaPlayer = MediaPlayer.create(this, R.raw.cute_character_wee_two).apply {
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
}


