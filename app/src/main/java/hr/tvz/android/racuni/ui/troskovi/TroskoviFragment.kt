package hr.tvz.android.racuni.ui.troskovi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import hr.tvz.android.racuni.shared.startActivity

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import hr.tvz.android.racuni.MainActivity
import hr.tvz.android.racuni.R
import hr.tvz.android.racuni.data.MainViewModel
import hr.tvz.android.racuni.shared.vratiStringSaDvijeDecimale
import kotlinx.android.synthetic.main.fragment_troskovi.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TroskoviFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_troskovi,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.troskovi_title)
        buttonViewPager.setOnClickListener {
            context?.startActivity<TroskoviActivity>()
        }
        mainViewModel.dohvatiUkupneTroskove().observe(viewLifecycleOwner, Observer {
            if(it!=null){
                troskovi_saldo.text = getString(R.string.troskovi_saldo,it.toString().vratiStringSaDvijeDecimale())
            }else{
                troskovi_saldo.text = getString(R.string.troskovi_saldo,"0")
            }
        })

    }
}