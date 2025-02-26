package com.tayyipgunay.thecountries2.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayyipgunay.thecountries2.R
import com.tayyipgunay.thecountries2.adapter.CountryAdapter
import com.tayyipgunay.thecountries2.ViewModel.CountryViewModel
import com.tayyipgunay.thecountries2.databinding.FragmentCountryBinding


class CountryFragment : Fragment() {

    // Binding nesnesi, fragmentin UI bileşenlerine erişim sağlar
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!! // Güvenli erişim için non-null getter

    private lateinit var viewModel: CountryViewModel // ViewModel nesnesi
    private val countryAdapter = CountryAdapter(arrayListOf()) // RecyclerView için adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewBinding kullanarak layout'u inflate ediyoruz
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel nesnesini bağlıyoruz (Fragment'in bağlı olduğu Activity üzerinden alınıyor)
        viewModel = ViewModelProvider(requireActivity()).get(CountryViewModel::class.java)

        viewModel.RefreshData() // Verileri güncelle

        // RecyclerView için layout ve adapter ayarlanıyor
        binding.countryList.layoutManager = LinearLayoutManager(requireContext())
        binding.countryList.adapter = countryAdapter

        // SwipeRefreshLayout kullanıcının listeyi yenilemesini sağlar
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.progressbar.visibility = View.VISIBLE

            viewModel.RefreshData() // Verileri yenile
            binding.swipeRefreshLayout.isRefreshing = false // Yenileme tamamlandığında kapat
        }

        observe() // LiveData gözlemleyici başlatılır
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Bellek sızıntılarını önlemek için binding'i null yapıyoruz
        _binding = null
    }


/*
ArrayList, verileri saklama, ekleme ve erişim işlevlerini yerine getiren bir koleksiyon sınıfıdır,
 ancak reaktif değildir; yani, listeye yapılan değişiklikleri otomatik olarak algılayıp UI'ye yansıtamaz.

LiveData ve MutableLiveData, Android'in reaktif veri yönetimi bileşenleridir ve
veri değişikliklerinin otomatik gözlemlenmesini sağlar. observe() metodu sayesinde,
 verideki değişiklikler otomatik olarak UI'ye yansıtılır.

MutableLiveData, LiveData'nın değiştirilebilir bir versiyonudur; bu sayede veriyi
 hem güncelleyebilir hem de gözlemleyebilirsiniz.

Ayrıca LiveData, Android bileşenlerinin yaşam döngüsüne duyarlıdır,
bu da veri gözlemlerinin sadece bileşen aktifken yapılmasını ve hafıza sızıntılarının önlenmesini sağlar. Bu yapı, özellikle MVVM mimarisinde, veri ve UI arasındaki otomatik senkronizasyon için kullanılır ve kodun daha temiz ve yönetilebilir olmasına katkı sağlar.
 */
    fun observe() {
        viewModel.countries.observe(viewLifecycleOwner,//viewLifecycleOwner, Fragment'in görünümünün yaşam döngüsünü temsil eder.
                                                      // Bu sayede, LiveData gözlemlerken sadece Fragment görünümü aktifken veri gözlemlenir.
            Observer { Countries ->
            Countries?.let { Countries ->//bütün bilgiler gelmiş ise binding.countryList.visibility = View.VISIBLE//
                binding.countryList.visibility=View.VISIBLE
                countryAdapter.UpdateCountryList(Countries)
              binding.progressbar.visibility = View.GONE//ortadaki progressbar yüklemeden sonra
                println("countries.observe  çalışıyor")//2

                binding.swipeRefreshLayout.isRefreshing = false // Burda yenileme animasyonu durdur

            }

        }

        )
        viewModel.countryError.observe(viewLifecycleOwner,
            Observer { Error ->
                if (Error) {
                   binding.countryError.visibility = View.VISIBLE//countryError text'i "errortryagain"
                    println("errorr var ")
                } else {
                   binding.countryError.visibility = View.GONE

                }

            }
        )
        viewModel.countryLoading.observe(viewLifecycleOwner,
            Observer { countryLoading ->
                if (countryLoading) {//veri çekme aşaması esnasında
                   binding.progressbar.visibility = View.VISIBLE
                   binding.countryList.visibility = View.GONE
                   binding.countryError.visibility = View.GONE
                    println("countryloading  çalışıyor")//1 her zaman çalışır

                } else {//ya veri gelmede hata var ya da success ama yükleme olup olmayacağı belli
                    println("countryloading else çalışıyor")//3
                // hem sucess hem hata olduğunda çalışır.
                    //ikisinde de veri çekme veya çekememe işlemi tamamlanmıştır çünkü

                }
            }
        )
    }
}

