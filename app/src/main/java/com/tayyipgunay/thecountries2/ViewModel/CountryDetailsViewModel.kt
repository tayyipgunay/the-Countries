package com.tayyipgunay.thecountries2.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayyipgunay.thecountries2.model.Country
import com.tayyipgunay.thecountries2.service.CountryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.swing.Swing


//class CountryDetailsViewModel:ViewModel()  {
class CountryDetailsViewModel(application: Application) : BaseViewModel(application) {

    val db = CountryDatabase(application) // Room veritabanı bağlantısı
    private val countryDao = db.countryDao() // DAO nesnesi üzerinden veritabanı işlemleri
    val countryLiveData = MutableLiveData<Country>() // Seçili ülkenin verisini tutan LiveData

    // Room veritabanından belirli bir ülke verisini getiren fonksiyon
    fun getDataFromRoom(uuid: Int) {
        launch(Dispatchers.IO) { // Arka planda çalıştırılan Coroutine başlatılır
            val country = countryDao.getCountry(uuid) // Belirtilen UUID'ye göre ülke verisini getir
            println("Fetched country from Room: $country")

            launch(Dispatchers.Main) { // Ana iş parçacığında güncelleme yapılır
                if (country != null) {
                    println("Setting countryLiveData with value: $country")
                    withContext(Dispatchers.Main) {
                        countryLiveData.value = country // LiveData'ya gelen veriyi ata
                    }
                } else {
                    println("Country is null, data not set in countryLiveData")
                }
            }
        }
    }
}




