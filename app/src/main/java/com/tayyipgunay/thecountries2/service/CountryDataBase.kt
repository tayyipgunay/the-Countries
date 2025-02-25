package com.tayyipgunay.thecountries2.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tayyipgunay.thecountries2.model.Country
import com.tayyipgunay.thecountries2.Util.Converters




    @Database(entities = arrayOf(Country::class),version = 1)
    @TypeConverters(Converters::class)
    abstract class CountryDatabase : RoomDatabase() {

        abstract fun countryDao() : CountryDao

        //Singleton

        companion object {

            @Volatile private var instance : CountryDatabase? = null
            // @Volatile: Bu açıklayıcı, instance değişkeninin her iş parçacığı tarafından
// güncel halinin ana bellekten okunmasını sağlar. Bu sayede, bir iş parçacığı
// değişkenin değerini güncellediğinde, diğer iş parçacıkları bu değişikliği hemen görebilir.
// Çok iş parçacıklı ortamlarda tutarlılık ve güvenlik sağlamak için kullanılır.

            private val lock = Any()

           /* operator fun invoke(context : Context) :CountryDatabase= instance ?: synchronized(lock) {
                instance ?: makeDatabase(context).also {
                    instance = it
                }
            }*/

            operator fun invoke(context: Context): CountryDatabase {

                // İlk kontrol: Eğer instance zaten oluşturulmuşsa, mevcut örneği döndür
                // Bu kontrol, gereksiz yere synchronized bloğuna girerek performansı düşürmemek için yapılır
                if (instance != null) {
                    return instance!!
                }

                /* Monitör: Bir nesneye veya kod bloğuna aynı anda yalnızca bir iş parçacığının erişmesini
                sağlamak için kullanılan bir kilit mekanizmasıdır. synchronized kullanıldığında, ilgili
                nesnenin monitörü kilitlenir ve diğer iş parçacıkları bekler. Bu, iş parçacıklarının
                sıralı bir şekilde çalışmasını sağlar ve eşzamanlılık (concurrency) sorunlarını önler. */

                // instance null ise, eşzamanlılık kontrolü sağlamak için synchronized bloğuna gir
                synchronized(lock) {
                    // İkinci kontrol: instance hala null ise, veritabanını oluştur
                    // Bu kontrol, çok iş parçacıklı bir ortamda veritabanı örneğinin sadece bir kez
                    // oluşturulmasını sağlar ve veri tutarlılığını korur
                    if (instance == null) {//eğer biri kapıda beklerken diğeri oluşturmuşsa diye kontrol ediyoruz.
                        instance = makeDatabase(context)
                    }
                    // instance'ı döndür
                    return instance!!
                }
            }


            private fun makeDatabase(context : Context) =
                Room.databaseBuilder(
                context.applicationContext,CountryDatabase::class.java,"countrydatabase"
            ).build()
        }
        /*private fun makeDatabase(context: Context): CountryDatabase {
            // Room veritabanı yapılandırıcısı oluşturuluyor
            return Room.databaseBuilder(
                context.applicationContext,            // Uygulamanın bağlamı (context)
                CountryDatabase::class.java,           // Veritabanı sınıfı
                "countrydatabase"                      // Veritabanı adı
            ).build()                                  // Veritabanını inşa ediyoruz
        }*/
    }






/*Singleton Yapısı: Kodunuzda CountryDatabase sınıfı, companion object içinde instance değişkeniyle Singleton
olarak tanımlanmıştır. Bu, veritabanı nesnesinin uygulama boyunca sadece bir kez oluşturulmasını
ve her seferinde aynı nesneye erişilmesini sağlar.Veritabanı Performansı: Veritabanı gibi kaynakların
her çağrıda tekrar tekrar oluşturulmasıperformans sorunlarına yol açabilir.
Singleton deseni sayesinde, uygulama genelinde aynı veritabanı nesnesi kullanılarak
gereksiz nesne oluşturma önlenir.*/



/*


db = Room.databaseBuilder(requireContext(), PlaceDataBase::class.java, "Place").build()
        placeDao = db.placeDao()
 */
