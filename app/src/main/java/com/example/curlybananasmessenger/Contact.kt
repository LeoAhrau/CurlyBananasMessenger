package com.example.curlybananasmessenger

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Contact(
    val contactId: String?,
    val contactName: String?,
    val contactEmail: String?
) : Serializable
