package hr.tvz.android.racuni.ui.troskovi

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.data.MainViewModel
import hr.tvz.android.racuni.models.Troskovi
import hr.tvz.android.racuni.shared.Utils.Companion.transformDate
import hr.tvz.android.racuni.shared.inflater
import hr.tvz.android.racuni.shared.toast
import kotlinx.android.synthetic.main.activity_troskovi.*
import kotlinx.android.synthetic.main.dodaj_izlazni_racun_dialog.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

const val RESTART = "restart"
const val PATTERN = "dd.MM.yyyy."

class TroskoviActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private var customPagerAdapter: CustomPagerAdapter? = null
    private var lista = listOf<String>()
    var d = 0
    var m = 0
    var y = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_troskovi)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        lista = resources.getStringArray(R.array.kategorije).toList()
        toolbar.title = lista[0]
        setSupportActionBar(toolbar)
        mainViewModel.dohvatiSveTroskove()
        mainViewModel.sviTroskovi.observe(this, androidx.lifecycle.Observer {
            if(it!=null){
                customPagerAdapter = CustomPagerAdapter(this,lista,it,mainViewModel,this)
                viewpager.adapter = customPagerAdapter
            }else{
                customPagerAdapter = CustomPagerAdapter(this,lista, listOf<Troskovi>(),mainViewModel,this)
                viewpager.adapter = customPagerAdapter
            }
            if(intent.getIntExtra(RESTART,-1)!=-1){
                viewpager.currentItem = intent.getIntExtra(RESTART,-1)
            }
        })

        viewpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> toolbar.title = (lista[position])
                    1 -> toolbar.title = (lista[position])
                    2 -> toolbar.title = (lista[position])
                    3 -> toolbar.title = (lista[position])
                    4 -> toolbar.title = (lista[position])
                    5 -> toolbar.title = (lista[position])
                    6 -> toolbar.title = (lista[position])
                    7 -> toolbar.title = (lista[position])
                    8 -> toolbar.title = (lista[position])
                    9 -> toolbar.title = (lista[position])
                    10 -> toolbar.title = (lista[position])
                    11-> toolbar.title = (lista[position])

                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_troskovi_racun,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.add_troskovi_racun){
            openDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View =
            this.inflater.inflate(R.layout.dodaj_izlazni_racun_dialog, LinearLayout(this), false)
        dialogView.uneseniDatum.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            val datePicker = DatePickerDialog(
                this
                ,
                DatePickerDialog.OnDateSetListener { _, pickedYear, monthOfYear, dayOfMonth ->
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
            if(dialogView.uneseniDatum.text.trim().isNotEmpty() && dialogView.uneseniKupac.text.trim()
                    .isNotEmpty() && dialogView.uneseniIznos.text.trim().isNotEmpty()
            ){
                val iznos = dialogView.uneseniIznos.text.toString().toDoubleOrNull()
                if(iznos!=null){
                    mainViewModel.unesiTroskoviRacun(Troskovi(dialogView.uneseniDatum.text.toString(),dialogView.uneseniKupac.text.toString(),
                        iznos,lista[viewpager.currentItem]))
                    this.toast(getString(R.string.uspjesno_unijeli_racun))
                    dialog.dismiss()
                    val intent = intent
                    intent.putExtra(RESTART,viewpager.currentItem)
                    finish()
                    startActivity(intent)
                }else{

                    this.toast(getString(R.string.unesite_ispravan_iznos_resource))
                }

            }else{
                this.toast(getString(R.string.unesite_sve_vrijednosti_resource))
            }
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

}
