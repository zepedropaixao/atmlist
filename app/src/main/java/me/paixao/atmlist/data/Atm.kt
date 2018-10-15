package me.paixao.atmlist.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

/**
 * Immutable model class for a Atm.
 */
@Entity(tableName = "atms")
data class Atm(
        @PrimaryKey
        var sonectId: String = "",
        var name: String = "",
        var latitude: String = "",
        var longitude: String = "",
        var imagePath: String = "",
        var address: String = ""
) {
        fun getLatLng(): LatLng = LatLng(latitude.toDouble(), longitude.toDouble())
}