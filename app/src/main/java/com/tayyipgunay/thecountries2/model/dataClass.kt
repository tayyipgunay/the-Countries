package com.tayyipgunay.thecountries2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Country(
    // Room veritabanı için tablo yapısı

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val countryName: String, // Ülke adı

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val countryRegion: String, // Ülkenin bulunduğu bölge

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val countryCapital: String, // Ülkenin başkenti

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val countryCurrency: String, // Ülkenin para birimi

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val countryLanguage: String, // Ülkenin resmi dili

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val flag: String, // Ülke bayrağı URL'si

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0 // Her kayıt için benzersiz ID (otomatik artırılır)
)
