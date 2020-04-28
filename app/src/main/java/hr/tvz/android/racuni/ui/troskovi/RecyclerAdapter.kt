package hr.tvz.android.racuni.ui.troskovi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.models.Troskovi
import hr.tvz.android.racuni.ui.izlazniracuni.IzlazniRacuniAdapter
import kotlinx.android.synthetic.main.item_izlazni_racun.view.*

class RecyclerAdapter(private val listaTroskova: List<Troskovi>): RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){

    private var listener: OnItemLongClickListener? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val datum: TextView = itemView.findViewById(R.id.datum)
        val kupac: TextView = itemView.findViewById(R.id.kupac)
        val iznos: TextView = itemView.findViewById(R.id.iznos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_izlazni_racun,parent,false))
    }

    override fun getItemCount(): Int = listaTroskova.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.datum.text = listaTroskova[position].datum
        holder.itemView.kupac.text = listaTroskova[position].kupac
        holder.itemView.iznos.text = listaTroskova[position].cijena.toString()
        holder.itemView.setOnLongClickListener {
            if(position!=RecyclerView.NO_POSITION){
                listener?.getLongItemClick(listaTroskova[position])
            }
            true
        }
    }

    interface OnItemLongClickListener{
        fun getLongItemClick(troskovi: Troskovi);
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener){
        this.listener = onItemLongClickListener
    }


}