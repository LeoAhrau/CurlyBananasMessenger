package com.example.curlybananasmessenger

import androidx.room.Entity
import androidx.room.PrimaryKey
//Create a Kotlin data class that describes your Entity (the contact). This class will represent
// a table in your SQLite database.

@Entity(tableName = "contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firestoreId: String,  // Håll Firestore ID som en sträng null eller icke??
    val name: String,
    val email: String?
)



//@Entity(tableName = "contacts")
//data class Contacts(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Local database ID
//    val firestoreId: String? = null, // Firestore document ID, optional
//    val name: String,
//    val email: String?
//)
//Eftersom Firestore använder sträng-ID:n och Room (i ditt fall) använder ett automatgenererat heltals-ID,
// behöver du generellt inte spara Firestore-ID:t i din Room-databas såvida du inte behöver referera tillbaka
// till Firestore. Om du dock behöver bibehålla en referens till Firestore-dokumentets ID, kan du lägga till
// ett ytterligare fält i din Contacts-klass, eventuellt som en nullbar sträng:

//@Entity(tableName = "contacts")
//data class Contacts(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val email: String?
//)
//

//@Entity
//data class Contacts(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val email: String?
//
//    )

