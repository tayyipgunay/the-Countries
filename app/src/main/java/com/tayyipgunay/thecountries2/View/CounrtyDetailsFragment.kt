package com.tayyipgunay.thecountries2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tayyipgunay.thecountries2.R
//import androidx.lifecycle.ViewModelProviders
import com.tayyipgunay.thecountries2.Util.DownloadUrl
import com.tayyipgunay.thecountries2.Util.placeHolderProgressBar
import com.tayyipgunay.thecountries2.ViewModel.CountryDetailsViewModel
import com.tayyipgunay.thecountries2.databinding.FragmentCounrtyDetailsBinding


class CounrtyDetailsFragment : Fragment() {

    // Binding nesnesi, UI bileşenlerine erişim sağlar
    private var _binding: FragmentCounrtyDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var countryDetailsViewModel: CountryDetailsViewModel // ViewModel nesnesi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data Binding kullanarak layout'u inflate ediyoruz
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_counrty_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel nesnesini oluşturuyoruz
        countryDetailsViewModel = ViewModelProvider(this).get(CountryDetailsViewModel::class.java)

        // Fragment'e gelen argümanları alıyoruz
        arguments?.let {
            val uuid = CounrtyDetailsFragmentArgs.fromBundle(it).uuid // Navigation Component ile gelen UUID değeri
            println("from adapter uuid: $uuid")
            println("from room uuid: $uuid")

            // ViewModel üzerinden Room veritabanından veriyi çekiyoruz
            countryDetailsViewModel.getDataFromRoom(uuid)
        }

        observeLiveData() // LiveData gözlemleyici başlatılır
    }

    private fun observeLiveData() {
        println("observeLiveDATA1")

        countryDetailsViewModel.countryLiveData.observe(viewLifecycleOwner, Observer { Country ->
            println("observeLiveDATA2") // Veri geldiğinde çalışır

            Country?.let {
                println("observeLiveDATA3")
                binding.selectedCountry = Country // Data Binding ile veriyi bağla
            }
        })
    }




    /*Observer { Country ->
            Country?.let {
                binding.countryName.text = Country.countryName
                binding.countryCapital.text = Country.countryCapital
                binding.countryCurrency.text = Country.countryCurrency
                binding.countryLanguage.text = Country.countryLanguage
                binding.countryimageid.DownloadUrl(Country.flag, placeHolderProgressBar(requireContext()))
               // uuid=Country.uuid



            }


        }

    )*/


        override fun onDestroyView() {
            super.onDestroyView()
            // Bellek sızıntılarını önlemek için binding'i null yapıyoruz
            _binding = null
        }

    }

