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

    // Binding nesnesi
    private var _binding: FragmentCounrtyDetailsBinding? = null
    private val binding get() = _binding!!
    //   private lateinit var binding:FragmentCounrtyDetailsBinding
    private lateinit var countryDetailsViewModel: CountryDetailsViewModel
    //private var uuid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding kullanarak layout'u inflate ediyoruz
        /*_binding = FragmentCounrtyDetailsBinding.inflate(inflater, container, false)
        return binding.root*/

      //  _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_counrty_details, container, false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_counrty_details, container, false)
        return binding.root
        /*binding=DataBindingUtil.inflate(inflater, R.layout.fragment_country,container,false)
        return binding.root*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryDetailsViewModel = ViewModelProvider(this).get(CountryDetailsViewModel::class.java)

        arguments?.let {

          var   uuid = CounrtyDetailsFragmentArgs.fromBundle(it).uuid//frameworkte tanımladığımız değişken
            println("from  adapter uuuid" + uuid)


            println("from room uuuid" + uuid)

            countryDetailsViewModel.getDataFromRoom(uuid)
        }


        observeLiveData()
    }

    private fun observeLiveData() {
        println("observeLiveDATA1")

        countryDetailsViewModel.countryLiveData.observe(viewLifecycleOwner, Observer { Country ->
            println("observeLiveDATA2") // Veri geldiğinde bu satır çalışır

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

