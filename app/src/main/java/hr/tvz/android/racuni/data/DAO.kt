package hr.tvz.android.racuni.data

import androidx.lifecycle.LiveData
import androidx.room.*
import hr.tvz.android.racuni.models.IzlazniRacun
import hr.tvz.android.racuni.models.Troskovi
import java.math.BigDecimal

@Dao
interface DAO {
    @Insert
    fun unesiIzlazniRacun(izlazniRacun: IzlazniRacun)

    @Query("SELECT * FROM IzlazniRacun ORDER BY substr(datum,4,2) ASC, substr(datum,1,2) ASC")
    fun dohvatiIzlazneRacune(): List<IzlazniRacun>

    @Query("SELECT SUM(iznos) as suma FROM IzlazniRacun")
    fun dohvatiIzlazniRacuniSaldo(): LiveData<Double>

    @Delete
    fun obrisiIzlazniRacun(izlazniRacun: IzlazniRacun)

    @Query("SELECT SUM(cijena) FROM Troskovi")
    fun dohvatiUkupneTroskove() :LiveData<Double>

    @Query("SELECT * FROM Troskovi ORDER BY substr(datum,4,2) ASC, substr(datum,1,2) ASC")
    fun dohvatiSveTroskove() :List<Troskovi>

    @Insert
    fun unesiTroskoviRacun(troskovi: Troskovi)

    @Delete
    fun obrisiTroskoviRacun(troskovi: Troskovi)

    @Query("SELECT SUM(iznos) FROM IzlazniRacun")
    fun sumaIzlaznihRacuna():Double

    @Query("SELECT SUM(cijena) FROM Troskovi")
    fun sumaTroskova():Double
}