package hr.tvz.android.racuni.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Troskovi")
data class Troskovi(
    var datum: String,
    var kupac: String,
    var cijena: Double,
    var vrsta: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}