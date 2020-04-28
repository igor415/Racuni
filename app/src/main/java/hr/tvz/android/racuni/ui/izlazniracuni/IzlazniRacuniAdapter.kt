package hr.tvz.android.racuni.ui.izlazniracuni

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.models.IzlazniRacun

class IzlazniRacuniAdapter(private val listaRacuna: List<IzlazniRacun>) : RecyclerView.Adapter<IzlazniRacuniAdapter.MyViewHolder>(){

    private var listener: OnItemLongClickListener? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var datum = itemView.findViewById<TextView>(R.id.datum)
        var kupac = itemView.findViewById<TextView>(R.id.kupac)
        var iznos = itemView.findViewById<TextView>(R.id.iznos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_izlazni_racun,parent,false)
        return MyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = listaRacuna.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val racun = listaRacuna[position]
        holder.datum.text = listaRacuna[position].datum
        holder.kupac.text = listaRacuna[position].kupac
        holder.iznos.text = listaRacuna[position].iznos.toString()
        holder.itemView.setOnLongClickListener {
            if(position!= RecyclerView.NO_POSITION){
                listener?.onClick(racun)
            }
            true
        }

    }

    interface OnItemLongClickListener {
        fun onClick(izlazniRacun: IzlazniRacun)
    }

    fun setOnItemClickListener(lis: OnItemLongClickListener) {
        listener = lis
    }
}