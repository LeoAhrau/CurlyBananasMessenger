package com.example.curlybananasmessenger

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore


class FirestoreRepository {

    fun addOrUpdateContact(contact: Contact) {
        var modifiableContact = contact  // Skapa en modifierbar kopia av kontakten
        if (modifiableContact.contactId == null) {
            // Generera ett nytt ID eller hantera fallet där ID saknas
            val newId = FirebaseFirestore.getInstance().collection("contacts").document().id
            modifiableContact = modifiableContact.copy(contactId = newId)
        }
        // Fortsätt med att lägga till eller uppdatera kontakten
        FirebaseFirestore.getInstance().collection("contacts").document(modifiableContact.contactId!!).set(modifiableContact)
    }

    fun getContacts(): LiveData<List<Contact>> {
        val result = MutableLiveData<List<Contact>>()
        FirebaseFirestore.getInstance().collection("contacts")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("FirestoreRepository", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val contactsList = snapshot?.documents?.map { doc ->
                    doc.toObject(Contact::class.java)!!.copy(contactId = doc.id)
                }
                result.value = contactsList ?: emptyList()
            }
        return result
    }
}
//var modifiableContact = contact: Skapar en modifierbar version av contact. Detta gör det möjligt att ändra contact inuti funktionen.
//contactId = newId: Tilldelar ett nytt ID till kontakten om det ursprungliga contactId är null.
//document(modifiableContact.contactId!!).set(modifiableContact): Uppdaterar eller lägger till dokumentet i Firestore med det nya eller existerande ID:t.
//Denna ändring ser till att din kod kan hantera uppdateringar av kontakter som initialt inte har något ID, vilket är ett vanligt scenario
// när nya kontakter skapas från användargränssnittet och ännu inte har blivit sparade i Firestore. Genom att korrekt hantera detta kan du
// säkerställa att din applikations dataintegritet upprätthålls.








//class FirestoreRepository {
//
//    fun addOrUpdateContact(contact: Contact) {
//        if (contact.contactId == null) {
//            // Generate a new ID or handle the case where ID is missing
//            val newId = FirebaseFirestore.getInstance().collection("contacts").document().id
//            contact = contact.copy(contactId = newId)
//        }
//        // Proceed with adding or updating the contact
//        FirebaseFirestore.getInstance().collection("contacts").document(contact.contactId!!).set(contact)
//    }
//
//    fun getContacts(): LiveData<List<Contact>> {
//        val result = MutableLiveData<List<Contact>>()
//        FirebaseFirestore.getInstance().collection("contacts")
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    Log.w("FirestoreRepository", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                val contactsList = snapshot?.documents?.map { doc ->
//                    doc.toObject(Contact::class.java)!!.copy(contactId = doc.id)
//                }
//                result.value = contactsList ?: emptyList()
//            }
//        return result
//    }
//}
