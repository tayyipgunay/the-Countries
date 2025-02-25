package com.tayyipgunay.thecountries2.service

import com.tayyipgunay.thecountries2.model.Country
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET


interface CountryAPI {//API çağrılarını tanımlamak ve yapılandırmak için kullanılır.
    //  base uri=   https://raw.githubusercontent.com/
// atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
// Bu satır, Retrofit kütüphanesine bir HTTP GET isteği yapılacağını belirtir.
// "atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json" kısmı,
// API'nin endpoint'idir. Bu endpoint, GitHub üzerinde barındırılan JSON formatındaki
// ülke verilerini içeren dosyaya işaret eder.
// Bu istek yapıldığında, belirtilen URL'den ülke verileri alınır.

    //fun getCountries():Single<List<Country>>
// Single kullanıyoruz çünkü bu işlev, GitHub'dan **tek seferlik** bir veri isteği yapıyor.
// her yenileme yapıldığında , sadece **bir kez** ülkelerin listesini çekmemiz yeterli.
   suspend fun getCountries():Response<List<Country>>
}