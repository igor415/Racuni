package hr.tvz.android.racuni.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.tvz.android.racuni.models.IzlazniRacun
import hr.tvz.android.racuni.models.Troskovi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    var izlazniRacuni = MutableLiveData<List<IzlazniRacun>>()
    var sviTroskovi = MutableLiveData<List<Troskovi>>()
    var ukupniSaldo = MutableLiveData<Double>()

    fun unesiIzlazniRacun(izlazniRacun: IzlazniRacun){
        viewModelScope.launch(Dispatchers.IO){
            repository.unesiIzlazniRacun(izlazniRacun)
        }
    }

    fun dohvatiIzlazneRacune(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.dohvatiIzlazneRacune()
            izlazniRacuni.postValue(result)
        }

    }
    fun dohvatiSveTroskove(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.dohvatiSveTroskove()
            sviTroskovi.postValue(result)
        }

    }

    fun dohvatiSaldo(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.dohvatiSaldo()
            ukupniSaldo.postValue(result)
        }
    }

    fun obrisiIzlazniRacun(izlazniRacun: IzlazniRacun){
        viewModelScope.launch(Dispatchers.IO) {
            repository.obrisiIzlazniRacun(izlazniRacun)
        }
    }

    fun dohvatiIzlazniRacuniSaldo(): LiveData<Double>{
        return repository.dohvatiIzlazniRacuniSaldo()
    }
    fun dohvatiUkupneTroskove():LiveData<Double>{
        return repository.dohvatiUkupneTroskove()
    }
    fun unesiTroskoviRacun(troskovi: Troskovi){
        viewModelScope.launch(Dispatchers.IO) {
            repository.unesiTroskoviRacun(troskovi)
        }
    }

    fun obrisiTroskoviRacun(troskovi: Troskovi){
        viewModelScope.launch(Dispatchers.IO) {
            repository.obrisiTroskoviRacun(troskovi)
        }
    }


}