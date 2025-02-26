package com.tayyipgunay.thecountries2.Util
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    // Room veritabanı için özel veri türü dönüştürücü
    // Bu sınıf, Room'un desteklemediği List<Int> türünü String'e ve tam tersine çevirmek için kullanılır

    @TypeConverter
    fun fromString(value: String): List<Int> {
        // JSON formatındaki String veriyi List<Int> türüne çeviriyoruz
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        // List<Int> türündeki veriyi JSON formatında String'e çeviriyoruz
        return Gson().toJson(list)
    }
}
