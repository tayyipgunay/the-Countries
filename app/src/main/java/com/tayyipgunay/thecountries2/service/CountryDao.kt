package com.tayyipgunay.thecountries2.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tayyipgunay.thecountries2.model.Country
import io.reactivex.Completable
import io.reactivex.Single

/*@Dao
interface CountryDao {
//Data Acess Object
    @Query("SELECT * FROM Country")
    suspend fun getAllCountry(): LiveData<List<Country>>
    // Bu metot, `Country` tablosundaki tüm kayıtları `List<Country>` türünde döndürür.

    @Query("SELECT * FROM Country WHERE uuid IN (:userIds)")
    suspend fun getCountry(userIds: List<Int>): List<Country>
    // Bu metot, belirtilen `userIds` dizisindeki UUID'ye sahip olan `Country` kaydını döndürür.
/*
@Query("SELECT * FROM Country WHERE uuid IN (:userIds)")
suspend fun getCountry(userIds: IntArray): List<Country>

 */
    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>
    // Bu metot, `Country` tablosuna bir veya birden fazla `Country` kaydı ekler ve eklenen kayıtların `Long` türünde birincil anahtar ID'lerini döndürür.
//ınt de olabilirdi.

    @Query("DELETE FROM Country")
    suspend fun allDelete()
    // Bu metot, `Country` tablosundaki tüm kayıtları siler.
}*/
/*@Dao
interface CountryDao {
    // Tüm verileri döndürür
    @Query("SELECT * FROM Country")
     fun getAllCountry(): LiveData<List<Country>>

    // Belirtilen UUID'lere göre kayıtları döndürür
    @Query("SELECT * FROM Country WHERE uuid IN (:userIds)")
    suspend fun getCountry(userIds: List<Int>): List<Country>

    // Kayıt ekler ve eklenen kayıtların ID'lerini döndürür
    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    // Tüm verileri siler
    @Query("DELETE FROM Country")
    suspend fun allDelete()
}
@Dao*/
/*@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys


    @Query("SELECT * FROM Country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()


}*/
/*@Dao
interface CountryDao {

    // Tüm verileri döndürür
    @Query("SELECT * FROM Country")
    fun getAllCountries(): LiveData<List<Country>> // MutableLiveData yerine LiveData kullanılıyor

    // Belirtilen UUID'lere göre kayıtları döndürür
    @Query("SELECT * FROM Country WHERE uuid IN (:userIds)")
    suspend fun getCountry(userIds: List<Int>): List<Country> // List olarak döndürülüyor

    // Kayıt ekler ve eklenen kayıtların ID'lerini döndürür
    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    // Tüm verileri siler
    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()
}*/
@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    //Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys


    @Query("SELECT * FROM Country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country


    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()

}




