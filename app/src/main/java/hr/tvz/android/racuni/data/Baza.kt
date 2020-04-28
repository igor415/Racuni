package hr.tvz.android.racuni.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.tvz.android.racuni.models.IzlazniRacun
import hr.tvz.android.racuni.models.Troskovi

@Database(entities = [IzlazniRacun::class,Troskovi::class],version = 6,exportSchema = false)
abstract class Baza : RoomDatabase() {
    abstract fun dao(): DAO
}