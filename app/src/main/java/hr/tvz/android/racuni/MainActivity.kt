package hr.tvz.android.racuni

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.tvz.android.racuni.ui.izlazniracuni.IzlazniRacuniFragment
import hr.tvz.android.racuni.ui.saldo.SaldoFragment
import hr.tvz.android.racuni.ui.troskovi.TroskoviFragment
import kotlinx.android.synthetic.main.activity_main.*

const val ROTATION = "fragmentRotation"

class MainActivity : AppCompatActivity() {

    private var fragmentNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                SaldoFragment()
            ).commit()
        } else {
            var selectedFragment: Fragment? = null
            fragmentNumber = savedInstanceState.getInt(ROTATION)
            when (fragmentNumber) {
                0 -> selectedFragment =
                    SaldoFragment()
                1 -> selectedFragment =
                    IzlazniRacuniFragment()
                2 -> selectedFragment =
                    TroskoviFragment()
            }
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment!!
            ).commit()
        }
        bottom_navigation.setOnNavigationItemSelectedListener(navListener)
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.saldo -> {
                    selectedFragment =
                        SaldoFragment()
                    fragmentNumber = 0
                }
                R.id.izlazni_racuni -> {
                    selectedFragment =
                        IzlazniRacuniFragment()
                    fragmentNumber = 1
                }
                R.id.troskovi -> {
                    selectedFragment =
                        TroskoviFragment()
                    fragmentNumber = 2
                }
            }
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment!!
            ).commit()

            true
        }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ROTATION, fragmentNumber)
    }
}
