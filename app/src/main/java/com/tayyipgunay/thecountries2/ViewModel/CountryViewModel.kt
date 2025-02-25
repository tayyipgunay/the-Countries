package com.tayyipgunay.thecountries2.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.tayyipgunay.thecountries2.model.Country
import com.tayyipgunay.thecountries2.Util.CustomSharedPrefences
import com.tayyipgunay.thecountries2.service.CountryApiService
import com.tayyipgunay.thecountries2.service.CountryDao
import com.tayyipgunay.thecountries2.service.CountryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.withContext

class CountryViewModel(application: Application) : BaseViewModel(application) {




    val countries = MutableLiveData<List<Country>>() // Ülke verilerini tutacak LiveData, veriler alındığında UI otomatik olarak güncellenir

    // val countries = ArrayList<Country>() // Normal bir ArrayList kullanıyoruz observe olmaz
    val countryError = MutableLiveData<Boolean>() // Hata durumunu tutan LiveData, hata oluşursa UI güncellenir
    var countryLoading = MutableLiveData<Boolean>() // Yüklenme durumunu tutan LiveData, veri yüklenirken UI'de yükleme animasyonu gösterilir
    private val disposable = CompositeDisposable() // API çağrıları sırasında oluşan abonelikleri yönetmek için kullanılır, bellek sızıntılarını önler


// customPreferences: CustomSharedPrefences sınıfının bir örneğini oluşturuyoruz
// Bu ifade, CustomSharedPrefences sınıfının invoke operatör fonksiyonunu çağırır
// Singleton deseni sayesinde, CustomSharedPrefences sınıfının tek bir örneği oluşturulur
// Eğer instance daha önce oluşturulmuşsa, mevcut örnek hemen döndürülür
// Eğer instance null ise, yeni bir CustomSharedPrefences örneği oluşturulur
// getApplication(): Uygulama bağlamını (Application Context) sağlar
  private var customPreferences = CustomSharedPrefences(getApplication())
    // invoke fonksiyonuna dolayısıyla instance nesnesine erişim sağlanıyor

    /* operator kullanarak sınıfı daha sade ve temiz bir şekilde çağırabilirdik, ancak operator olmadan böyle yazmalıyız
    private var customPreferences = CustomSharedPrefences.invoke(getApplication())*/


    // refreshTime: Verilerin güncellenme sıklığını nanosaniye cinsinden belirler
// 10 dakika = 10 * 60 * 1000 * 1000 * 1000 nanosaniye
// Bu, verilerin 10 dakikada bir güncellenmesi gerektiğini ifade eder
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L//10 dakika


    //private val countryApiService = CountryApiService() // API ile iletişim kurmak için kullanılan servis sınıfı

    /* private var db = Room.databaseBuilder(application, CountryDatabase::class.java, "Country").build()
    private var countryDao = db.countryDao()*/

    val db = CountryDatabase(application)
    private val countryDao = db.countryDao()

    /*Burada CountryDatabase'in invoke fonksiyonu çağrılıyor (eğer invoke fonksiyonu tanımlanmışsa).
    invoke fonksiyonu, Singleton deseni kullanarak veritabanının tek bir örneğini oluşturur ve bu örneği döndürür.
*/
    fun RefreshData() {
        countryError.value = false // Veri yenilenirken hata durumu baştan false olarak ayarlanır
        GetDataFromAPI() // API'den veri çekme işlemi başlatılır

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {//Verilerin güncellik durumunu kontrol eder. updateTime null değil, 0L değil ve
            // 10 dakikadan az bir süre geçmişse
// (refreshTime süresi dolmamışsa), veriyi yeniden çekmeye gerek yoktur.

            getDataFromSqlLite()
            Toast.makeText(getApplication(), "getDataFromSqlLite metodu çalıştı", Toast.LENGTH_LONG)
                .show()

        } else {
            GetDataFromAPI()
            Toast.makeText(getApplication(), "GetDataFromAPI metodu çalıştı", Toast.LENGTH_LONG)
                .show()

        }
    }

    private fun GetDataFromAPI() {
            countryLoading.value = true // Veri yüklenmeye başlandığında, yüklenme durumunu true olarak ayarlıyoruz

            launch(Dispatchers.IO) {
                try {
                    println("IO içinde başlıyor")

                    // API çağrısı
                    val response = CountryApiService.getCountries()
                    println("Veri çekildi, API çağrısı tamamlandı")
                    // Ana iş parçacığına geçiş
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            response.body()?.let { responseData ->

                                //println("Başarılı yanıt alındı: ${responseData.size} ülke")

                                // Başarılı yanıtı işleyin
                                showCountries(responseData)
                                storeinSqlLite(responseData)
                              /*  countries.value = responseData
                                countryError.value = false
                                countryLoading.value = false*/
                            } ?: run{
                                println("Yanıt gövdesi null")
                                countryError.value = true
                                countryLoading.value = false
                            }
                        } else {
                            println("Yanıt başarısız: ${response.code()}")
                            countryError.value = true
                            countryLoading.value = false
                        }
                    }
                } catch (e: Exception) {
                    // Hata durumunu yakala ve günlüğe yazdır
                    println("Hata oluştu: ${e.message}")
                    withContext(Dispatchers.Main) {
                        countryError.value = true
                        countryLoading.value = false
                    }
                }
            }





        /*   disposable.add(
               //   countryApiService.getCountries()//singleton ile ihtiyaç duyulmadı
               CountryApiService.getCountries()//simgleton ile global sınıf adı ile erişim sağlandı

                   // .subscribeOn(Schedulers.newThread()) Her çağrıda yeni bir iş parçacığı oluşturur,
                   // bu kaynak tüketimini artırabilir.
                   .subscribeOn(Schedulers.io()) // I/O işlemleri için optimize edilmiştir,
                   // iş parçacığı havuzunu kullanarak daha verimli çalışır
                   .observeOn(AndroidSchedulers.mainThread()) // Sonuçlar ana iş parçacığında
                   // gözlemlenir ve UI güncellemeleri yapılabilir

                   .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                       /*
                       DisposableSingleObserver, Single türündeki veri akışını dinlemek ve sonucu almak için kullanılır.
                     Single observable, sadece bir kez veri döndürür veya bir hata bildirir. DisposableSingleObserver,
                        bu iki durumu yönetir.
                        */

                       override fun onSuccess(countryList: List<Country>) {
                           println(countryList) // Başarılı sonuçlar konsola yazılır
                           storeinSqlLite(countryList)
                           println("veri çekildi apiden")
                           showCountries(countryList)


                           /*val countries = ArrayList<Country>() // Normal bir ArrayList tanımlandı
                           countries.value = p0 // Hata verir çünkü ArrayList'in value diye bir özelliği yoktur*/
                           // LiveData ile gelen ülke verileri güncellenir ve UI güncellenir
                           /* countries.value=p0

                           countryError.value = false // Hata olmadığı bilgisi güncellenir
                           countryLoading.value = false*/ // Yüklenme tamamlandığında, yüklenme durumu false olarak ayarlanır


                       }

                       override fun onError(p0: Throwable) {
                           countryLoading.value = false // Yüklenme sırasında hata oluşursa yüklenme durumu false olarak ayarlanır
                           countryError.value = true // Hata durumu güncellenir ve UI'ye hata gösterimi yapılır
                           println(p0.message) // Hata mesajı konsola yazılır
                       }

                   })
           )*/
    }

    fun storeinSqlLite(countryList: List<Country>) {

        launch(Dispatchers.IO) {
            println("Running on thread: ${Thread.currentThread().name}")

            // Veritabanı işlemlerini başlatmak için Coroutine kullanıyoruz
            // (arka planda çalışarak ana iş parçacığını bloke etmez)

            // Room veritabanına tüm Country nesnelerini ekliyoruz.
            // countryDao.insertAll, her bir Country nesnesi için oluşturulan UUID'leri içeren bir liste (List<Long>) döner.
            val listLong = countryDao.insertAll(*countryList.toTypedArray())
            // Döngü başlatılıyor: Her bir Country nesnesi için UUID değerini atıyoruz
            withContext(Dispatchers.Main) {
                var i = 0
                // Döngü: countryList listesindeki her bir Country nesnesi için işlem yapıyoruz
                while (i < countryList.size) {
                    // Room veritabanı tarafından her bir Country nesnesi için otomatik olarak oluşturulan UUID değerini,
                    // countryList listesindeki ilgili Country nesnesinin uuid alanına atıyoruz.
                    // Bu işlem, liste ile veritabanı arasında tutarlılığı sağlar.
                    // Amaç: Veritabanına eklenen Country nesnelerinin benzersiz UUID'lerini listeye geri atamak
                    // Bu sayede, daha sonra güncelleme veya silme işlemleri sırasında nesneler veritabanındaki karşılıklarıyla eşleşir.
                    countryList[i].uuid = listLong[i].toInt()

                    // Bir sonraki Country nesnesine geçmek için sayaç artırılır
                    i += 1
                }


                customPreferences.saveTime(System.nanoTime())
                println("saat "+System.nanoTime())
                //Güncel zamanı (nanosaniye cinsinden) SharedPreferences'a kaydeder.
            }
        }
    }

    fun getDataFromSqlLite() {
        //  CoroutineScope(Dispatchers.IO).launch {
        countryLoading.value = true
        launch(Dispatchers.IO) {
            //val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()

            val countries = countryDao.getAllCountries()
            withContext(Dispatchers.Main) {
                showCountries(countries)
                //global exception handler bak
            }

        }
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }
}