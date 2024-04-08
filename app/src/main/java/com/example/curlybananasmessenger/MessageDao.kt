package com.example.curlybananasmessenger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
//I det här kodblocket definieras MessageDao, ett Data Access Object (DAO) som är en del av Room-databasbiblioteket i Android.
// DAO:er används för att abstrahera bort databasåtkomster och erbjuder en ren API för databasoperationer.

@Dao //@Dao är en annotation som indikerar att gränssnittet MessageDao är ett DAO som ska användas med Room för att hantera databasoperationer.
interface MessageDao {
    @Query("SELECT * FROM Message WHERE userId = :userId ORDER BY timestamp DESC")
    fun getMessagesForUser(userId: Int): LiveData<List<Message>> // Metoden returnerar ett LiveData-objekt som innehåller en lista av Message-objekt.
    // Användningen av LiveData gör att UI-komponenter kan observera datan och uppdateras automatiskt när datan ändras i databasen

//
//    @Query("SELECT * FROM Message WHERE userId = :userId ORDER BY timestamp DESC")
//    suspend fun getMessagesForUser(userId: Int): List<Message> //metoden med suspend modifieraren är avsedd att användas med Kotlin Coroutines för asynkron exekvering, men utan LiveData för automatiska UI-uppdateringar.

    @Insert
    suspend fun insert(message: Message)

    @Delete
    suspend fun delete(message: Message) //suspend-modifieraren =för att kunna köras asynkront inom en coroutine.
}
