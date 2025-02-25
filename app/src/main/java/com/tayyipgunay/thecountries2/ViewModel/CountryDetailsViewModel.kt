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

class CountryDetailsViewModel(application: Application):BaseViewModel(application) {
    val db = CountryDatabase(application)
    private val countryDao = db.countryDao()
    val countryLiveData = MutableLiveData<Country>()




    fun getDataFromRoom(uuid: Int) {
        // Coroutine başlatırken exception handler'ı kullanıyoruz
        // Coroutine başlatırken BaseViewModel'deki exception handler'ı otomatik olarak kullanır
        launch(Dispatchers.IO) {

            val country = countryDao.getCountry(uuid)
            println("Fetched country from Room: $country")

            launch(Dispatchers.Main) {

                if (country != null) {
                    println("Setting countryLiveData with value: $country")
                    withContext(Dispatchers.Main) {
                        countryLiveData.value = country
                    }
                } else {
                    println("Country is null, data not set in countryLiveData")
                }
                //}
            }
        }
    }
}



