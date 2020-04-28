package hr.tvz.android.racuni.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "IzlazniRacun")
data class IzlazniRacun(
    var datum: String,
    var kupac: String,
    var iznos: Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


