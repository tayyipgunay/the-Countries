package com.tayyipgunay.thecountries2.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPrefences {

    companion object {

        // SharedPreferences anahtar adını tanımlıyoruz
        private val PREFERENCES_TIME = "preferences_time"

        // SharedPreferences nesnesi, veriyi saklamak ve almak için kullanılır
        private var sharedPreferences: SharedPreferences? = null

        // Singleton örneği için değişken
        @Volatile private var instance: CustomSharedPrefences? = null
        // @Volatile: Java'dan gelen bir kavramdır. Değişkenin güncel halini her iş parçacığı
// ana bellekten okur, böylece iş parçacıklarının değişkeni yerel bellekte önbelleğe alması önlenir.
// Bu, değişikliğin tüm iş parçacıkları tarafından hemen görülmesini sağlar.
// @Volatile varsa: Değişken ana bellekte tutulur; her iş parçacığı en güncel değeri ana bellekten alır.
// @Volatile yoksa: Değişken iş parçacıklarının yerel belleğinde önbelleğe alınabilir, bu da eski değerin kullanılmasına yol açabilir.
//
// Özet: @Volatile, iş parçacıklarının her zaman güncel ve tutarlı veri kullanmasını sağlar.


        // Eşzamanlılık için kilit objesi
        // lock: Eşzamanlılık kontrolü için Any sınıfından oluşturulan kilit nesnesi
        private val lock = Any()

       /* Diyelim ki bir oda var ve bu odada aynı anda sadece bir kişinin çalışmasına izin veriliyor.

        Oda kilidi (lock nesnesi): Odanın kapısını kilitlemek için kullanılan bir anahtar/kilit.
        synchronized(lock): Kapıyı kilitleyip çalışmaya başlıyor ve sadece bir kişi içeri girebiliyor. Diğerleri kapının kilidinin açılmasını bekliyor.
*/




        // invoke operatörü, sınıfı bir nesne gibi çağırarak CustomSharedPrefences örneği döndürmek için kullanılır
        /*operator fun invoke(context: Context): CustomSharedPrefences = instance ?: synchronized(lock) {
            // synchronized(lock): Birden fazla iş parçacığının aynı anda bu bloğa girmesini önlemek için
            // Eğer instance null ise, makeCustomSharedPreferences fonksiyonu ile örnek oluşturulur
            instance ?: makeCustomSharedPreferences(context).also {
                // Yeni oluşturulan örneği instance değişkenine atar
                instance = it
            }
        }*/
        // invoke operatörü, sınıfı bir nesne gibi çağırarak CustomSharedPrefences örneği döndürmek için kullanılır
        operator fun invoke(context: Context): CustomSharedPrefences {//invoke operatörü, bir sınıf örneğinin fonksiyon gibi çağrılabilmesini sağlar.
       //     Bu, kodu daha sade ve kullanımı daha kolay hale getirir.
            // İlk kontrol: Eğer instance zaten oluşturulmuşsa, mevcut örneği döndür
            // Böylece synchronized bloğuna girmeye gerek kalmaz ve performans artar
            if (instance != null) {
                return instance!!
            }

            // Eşzamanlılık kontrolü sağlamak için synchronized bloğu kullan
            // Aynı anda birden fazla iş parçacığının bu bloğu çalıştırmasını önler
            //A bekliyor mesela B oluşturusa a tekrar oluşturmasın diye ikinci null kontrolü yapıyoruz.
            synchronized(lock) {
                //B içeride oluşturuyor
                if (instance == null) {
                    instance = makeCustomSharedPreferences(context)//
                }
                // Oluşturulan veya mevcut örneği döndür
                return instance!!
            }
        }

        // SharedPreferences nesnesini oluşturmak için fonksiyon
        private fun makeCustomSharedPreferences(context: Context): CustomSharedPrefences {
            // Uygulamanın varsayılan SharedPreferences'ını alıyoruz
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            // Uygulamanın genel ayarlarını saklamak için varsayılan SharedPreferences dosyasını oluşturur veya mevcut olanı döner.
// Dosya adı sistem tarafından otomatik olarak belirlenir ve uygulama genelinde erişilebilir.
            // Yeni bir CustomSharedPrefences örneği döndürüyoruz
            return CustomSharedPrefences()
            //val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
          //  val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        }
    }

    // Zamanı SharedPreferences'a kaydeden fonksiyon
    fun saveTime(time: Long) {
        // SharedPreferences üzerinde değişiklik yapıyoruz
        // commit = true: Değişiklikleri senkron olarak kaydetmemizi sağlar.
        // Bu, işlemin tamamlanmasını bekler ve verilerin hemen kaydedilmesini garanti eder.
        // Ana iş parçacığında yavaşlama yaratabileceğinden sadece kritik durumlarda kullanılır.
        sharedPreferences?.edit(commit = true) {
            // Long tipindeki zaman değerini PREFERENCES_TIME anahtarıyla kaydediyoruz
            putLong(PREFERENCES_TIME, time)
        }
    }
    /*
    fun saveTime(time: Long) {
    sharedPreferences?.edit {
        // Long tipindeki zaman değerini PREFERENCES_TIME anahtarıyla kaydediyoruz
        putLong(PREFERENCES_TIME, time)
        apply() // Değişiklikleri asenkron olarak kaydeder
    }
     */

    // Kaydedilmiş zamanı alan fonksiyon, varsayılan olarak 0 döner
//fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME, 0)
fun getTime(): Long {

return sharedPreferences?.getLong(PREFERENCES_TIME,0) ?: 0

}
    /*fun getTime(): Long? {

        return sharedPreferences?.getLong(PREFERENCES_TIME,0)

    }*/
}
