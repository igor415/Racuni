package hr.tvz.android.racuni.ui.saldo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import hr.tvz.android.racuni.MainActivity
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.data.MainViewModel
import hr.tvz.android.racuni.shared.vratiStringSaDvijeDecimale
import kotlinx.android.synthetic.main.fragment_saldo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SaldoFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_saldo,container,false)

        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.saldo_title)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.dohvatiSaldo()
        mainViewModel.ukupniSaldo.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                saldoValue.text = it.toString().vratiStringSaDvijeDecimale()
            }else{
                saldoValue.text = getString(R.string.saldo_nedostupan_resource)
            }

        })
    }

}