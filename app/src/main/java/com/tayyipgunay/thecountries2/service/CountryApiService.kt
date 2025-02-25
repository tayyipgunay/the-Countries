package com.tayyipgunay.thecountries2.service

import com.tayyipgunay.thecountries2.model.Country
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//class CountryApiService {
object CountryApiService{// CountryApiService, Retrofit'i yapılandırır ve API çağrısını gerçekleştirmek için CountryAPI arayüzünü kullanır.
    // Singleton yapısı, performansı artırır ve kaynak kullanımını optimize eder.
    /* Singleton ile Ne Sağlanır?
    Tek Bir Nesneyle Global Erişim: Uygulamanın her yerinde aynı Service örneğini kullanmak mümkündür.
    Daha Az Bellek ve İşlem Yükü: Gereksiz nesne oluşturmanın önüne geçilir.
    // API'nin temel URL'si, veriler buradan alınacak*/

    private val BASE_URL = "https://raw.githubusercontent.com/"//değişmeyecek veriler BASE_URL büyük harfler ile kaydedilir.değişken olarak

    // Retrofit nesnesi oluşturuluyor ve CountryAPI arayüzüne bağlıyor
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL) // API'nin temel URL'si ayarlanıyor
        .addConverterFactory(GsonConverterFactory.create()) // JSON verisini dönüştürmek için Gson kullanılıyor
      //  .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava2 kullanarak çağrıların asenkron yapılabilmesi sağlanıyor
        .build()
        .create(CountryAPI::class.java) // API arayüzü oluşturuluyor

    // Ülkelerin listesini döndüren fonksiyon
    // getCountries() metodu, API'den ülke verilerini alır ve Single tipinde döndürür

    /*fun getCountries(): Single<List<Country>> {//bunu suspend ile yap
        return api.getCountries()//
        // Single kullanıyoruz çünkü bu işlev, GitHub'dan **tek seferlik** bir veri isteği yapıyor.

    }*/
    suspend fun getCountries():Response<List<Country>>{
        return api.getCountries()//suspend ile de yapılabilir.
    }

}