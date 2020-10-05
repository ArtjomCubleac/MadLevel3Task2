package com.example.madlevel3task2


import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Portal(
    var PortalTitle: String?,
    var URL: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(), parcel.readString()
    )

    companion object : Parceler<Portal> {

        override fun Portal.write(parcel: Parcel, flags: Int) {
            parcel.writeString(PortalTitle)
            parcel.writeString(URL)
        }

        override fun create(parcel: Parcel): Portal {
            return Portal(parcel)
        }
    }

    override fun toString(): String {
        return "$PortalTitle\n\n$URL"
    }

}