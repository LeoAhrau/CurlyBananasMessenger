package com.example.curlybananasmessenger


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
//ett Data Access Object (DAO) för att hantera åtkomst till användarinformationen
@Dao //@Dao är en annotation som markerar gränssnittet UserDao som ett DAO som ska användas med Room. Det indikerar att detta gränssnitt är ansvarigt för att hantera databasåtkomsten för User-entiteter.
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //specifierar vad som ska hända om det finns en konflikt vid insättningen, i detta fall att den befintliga posten ska ersättas med den nya. Det är användbart för att uppdatera en användares data utan att explicit definiera en uppdateringsmetod.
    suspend fun insertUser(user: com.example.curlybananasmessenger.User)

    @Query("SELECT * FROM users WHERE userId = :userId") //definierar en SQL-fråga för att hämta en användare med ett specifikt userId från users-tabellen i databasen. Den använder en parameter :userId som ersätts av metoden getUserById när den anropas.
    fun getUserById(userId: String): LiveData<com.example.curlybananasmessenger.User>

    // Lägg till fler metoder efter behov, som att uppdatera eller ta bort användare


}
*/

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class UserDao {

    val KEY_ID = "id"
    val KEY_Nickname = "nickname"
    val KEY_USERNAME = "username"
    val KEY_PASSWORD = "password"

    fun registerUser(user: User){
        val userDetails = HashMap<String, Any>()

        userDetails[KEY_ID] = user.id as Any
        userDetails[KEY_Nickname] = user.nickname as Any
        userDetails[KEY_USERNAME] = user.username as Any
        userDetails[KEY_PASSWORD] = user.password as Any

        FirebaseFirestore
            .getInstance()
            .document("users/${user.id}")
            .set(userDetails)
            .addOnSuccessListener { Log.i("SUCCESS", "Successfully registered user") }
            .addOnFailureListener { Log.e("FAILURE", "Failed to register user") }

    }


}

