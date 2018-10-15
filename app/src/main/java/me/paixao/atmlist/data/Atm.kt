package me.paixao.atmlist.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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
)