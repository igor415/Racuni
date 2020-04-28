package hr.tvz.android.racuni.ui.izlazniracuni

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import hr.tvz.android.racuni.MainActivity
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.data.MainViewModel
import hr.tvz.android.racuni.models.IzlazniRacun
import hr.tvz.android.racuni.shared.Utils.Companion.negativeButtonClick
import hr.tvz.android.racuni.shared.Utils.Companion.transformDate
import hr.tvz.android.racuni.shared.inflater
import hr.tvz.android.racuni.shared.toast
import hr.tvz.android.racuni.shared.vratiStringSaDvijeDecimale
import hr.tvz.android.racuni.ui.troskovi.PATTERN
import kotlinx.android.synthetic.main.dodaj_izlazni_racun_dialog.view.*
import kotlinx.android.synthetic.main.dodaj_izlazni_racun_dialog.view.uneseniDatum
import kotlinx.android.synthetic.main.fragment_izlazni_racuni.*
import kotlinx.android.synthetic.main.fragment_izlazni_racuni.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class IzlazniRacuniFragment : Fragment(), IzlazniRacuniAdapter.OnItemLongClickListener {
    var d = 0
    var m = 0
    var y = 0
    private lateinit var deleteRacun: IzlazniRacun
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_izlazni_racuni, container, false)
        mainViewModel.dohvatiIzlazneRacune()
        mainViewModel.izlazniRacuni.observe(viewLifecycleOwner, Observer {
            var adapter =
                IzlazniRacuniAdapter(it)
            if (it.isNullOrEmpty()) {
                val list: List<IzlazniRacun> = listOf()
                adapter =
                    IzlazniRacuniAdapter(
                        list
                    )

            }
            adapter.setOnItemClickListener(this)
            root.recyclerView.adapter = adapter
        })

        root.recyclerView.layoutManager = LinearLayoutManager(context)
        root.recyclerView.setHasFixedSize(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.izlazni_racuni_title)
        mainViewModel.dohvatiIzlazniRacuniSaldo().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                izlazni_racuni_saldo.text =
                    getString(R.string.izlazni_racuni_saldo_resource, it.toString().vratiStringSaDvijeDecimale())
            } else {
                izlazni_racuni_saldo.text = getString(R.string.izlazni_racuni_saldo_resource, "0.0")
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_izlazni_racun, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.dodaj_izlazni_racun) {
            openDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val dialogView: View =
            context!!.inflater.inflate(
                R.layout.dodaj_izlazni_racun_dialog,
                LinearLayout(activity),
                false
            )
        dialogView.uneseniDatum.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            val datePicker = DatePickerDialog(
                context!!,
                OnDateSetListener { _, pickedYear, monthOfYear, dayOfMonth ->
                    d = dayOfMonth
                    m = monthOfYear
                    y = pickedYear
                    dialogView.uneseniDatum.setText(transformDate(d, m, y))
                }, year, month, day
            )
            datePicker.show()

        }
        builder.setView(dialogView)
        val dialog: AlertDialog
        dialog = builder.create()
        dialogView.spremi.setOnClickListener {
            if (dialogView.uneseniDatum.text.trim()
                    .isNotEmpty() && dialogView.uneseniKupac.text.trim()
                    .isNotEmpty() && dialogView.uneseniIznos.text.trim().isNotEmpty()
            ) {
                val iznos = dialogView.uneseniIznos.text.toString().toDoubleOrNull()
                if (iznos != null) {
                    val i = IzlazniRacun(
                        dialogView.uneseniDatum.text.toString(),
                        dialogView.uneseniKupac.text.toString(),
                        iznos
                    )
                    mainViewModel.unesiIzlazniRacun(i)
                    context.toast(getString(R.string.uspjesno_unijeli_racun))
                    dialog.dismiss()
                    mainViewModel.dohvatiIzlazneRacune()
                } else {

                    context.toast(getString(R.string.unesite_ispravan_iznos_resource))
                }


            } else {
                context.toast(getString(R.string.unesite_sve_vrijednosti_resource))
            }
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }



    override fun onClick(izlazniRacun: IzlazniRacun) {
        deleteRacun = izlazniRacun
        deleteAlert()
    }

    val positiveButtonClick = { _: DialogInterface, _: Int ->
        obrisiRacun()
    }

    fun obrisiRacun() {
        if (::deleteRacun.isInitialized) {
            mainViewModel.obrisiIzlazniRacun(deleteRacun)
            context.toast(getString(R.string.racun_obrisan_resource))
        } else {
            context.toast(getString(R.string.greska_brisanje_racuna_resource))
        }

    }

    fun deleteAlert() {

        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setMessage(getString(R.string.pitanje_obrisi_racun))
            setPositiveButton(
                getString(R.string.da_resource),
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            setNegativeButton(getString(R.string.ne_resource), negativeButtonClick)
            show()
        }


    }


}