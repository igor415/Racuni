package hr.tvz.android.racuni.data

import androidx.lifecycle.LiveData
import hr.tvz.android.racuni.models.IzlazniRacun
import hr.tvz.android.racuni.models.Troskovi

class Repository(private val db: Baza){

    fun unesiIzlazniRacun(izlazniRacun: IzlazniRacun){
        db.dao().unesiIzlazniRacun(izlazniRacun)
    }
    fun dohvatiIzlazneRacune(): List<IzlazniRacun>{
        return db.dao().dohvatiIzlazneRacune()
    }

    fun dohvatiSaldo():Double{
        val d1 = db.dao().sumaIzlaznihRacuna()
        val d2 = db.dao().sumaTroskova()
        return d1-d2
    }

    fun obrisiIzlazniRacun(izlazniRacun: IzlazniRacun){
        return db.dao().obrisiIzlazniRacun(izlazniRacun)
    }

    fun dohvatiIzlazniRacuniSaldo(): LiveData<Double>{
        return db.dao().dohvatiIzlazniRacuniSaldo()
    }
    fun dohvatiUkupneTroskove():LiveData<Double>{
        return db.dao().dohvatiUkupneTroskove()
    }
    fun unesiTroskoviRacun(troskovi: Troskovi){
        db.dao().unesiTroskoviRacun(troskovi)
    }
    fun dohvatiSveTroskove() :List<Troskovi>{
        return db.dao().dohvatiSveTroskove()
    }
    fun obrisiTroskoviRacun(troskovi: Troskovi){
        db.dao().obrisiTroskoviRacun(troskovi)
    }
}