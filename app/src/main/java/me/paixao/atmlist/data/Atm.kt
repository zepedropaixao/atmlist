package me.paixao.atmlist.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Immutable model class for a Atm.
 */
@Entity(tableName = "atms")
data class Atm(
        @PrimaryKey
        val sonectId: String,
        val name: String,
        val latitude: String,
        val longitude: String,
        val imagePath: String,
        var address: String
)