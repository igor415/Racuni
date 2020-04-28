package hr.tvz.android.racuni.ui.troskovi

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.data.MainViewModel
import hr.tvz.android.racuni.models.Troskovi
import hr.tvz.android.racuni.shared.Utils.Companion.negativeButtonClick
import hr.tvz.android.racuni.shared.toast
import hr.tvz.android.racuni.shared.vratiStringSaDvijeDecimale
import kotlinx.android.synthetic.main.viewpager_list_1.view.*

class CustomPagerAdapter(private val context: Context,private val list: List<String>,private val listaTroskova: List<Troskovi>,private val mainViewModel: MainViewModel,
    private val activity: Activity) :
    PagerAdapter(),RecyclerAdapter.OnItemLongClickListener {

    var recyclerAdapter: RecyclerAdapter? = null
    lateinit var deleteRacun: Troskovi


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.viewpager_list_1, container, false) as ViewGroup
        var suma = 0.0
        val filtriranaLista = mutableListOf<Troskovi>()
        listaTroskova.forEach {
            if(it.vrsta==list[position]){
                suma += it.cijena
                filtriranaLista.add(it)
            }
        }
        val text = layout.findViewById<TextView>(R.id.troskovi_saldo_kategorija)
        if(suma>0){
            text.text = context.getString(R.string.troskovi_saldo,suma.toString().vratiStringSaDvijeDecimale())
        }else{
            text.text = context.getString(R.string.troskovi_saldo,suma.toString())
        }

        recyclerAdapter = RecyclerAdapter(filtriranaLista)
        recyclerAdapter?.setOnItemLongClickListener(this)
        layout.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        container.addView(layout)
        return layout
    }



    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int = list.size

    override fun getLongItemClick(troskovi: Troskovi) {
        deleteRacun = troskovi
        deleteAlert()
    }

    val positiveButtonClick = { _: DialogInterface, _: Int ->
        obrisiRacun()
    }

    fun obrisiRacun() {
        if(::deleteRacun.isInitialized){
            mainViewModel.obrisiTroskoviRacun(deleteRacun)
            context.toast(context.getString(R.string.racun_obrisan_resource))
            val index = list.indexOf(deleteRacun.vrsta)
            val intent = activity.intent
            intent.putExtra(RESTART,index)
            activity.finish()
            activity.startActivity(intent)
        }else{
            context.toast(context.getString(R.string.greska_brisanje_racuna_resource))
        }

    }

    fun deleteAlert(){

        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setMessage(context.getString(R.string.pitanje_obrisi_racun))
            setPositiveButton(context.getString(R.string.da_resource), DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton(context.getString(R.string.ne_resource), negativeButtonClick)
            show()
        }


    }

}