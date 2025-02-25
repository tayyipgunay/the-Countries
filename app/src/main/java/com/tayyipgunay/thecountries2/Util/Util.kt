package com.tayyipgunay.thecountries2.Util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Option
import com.bumptech.glide.request.RequestOptions
import com.tayyipgunay.thecountries2.R


// ImageView sınıfına bir uzantı fonksiyonu ekliyoruz
fun ImageView.DownloadUrl(Url: String?, circularProgressDrawable: CircularProgressDrawable) {
    // Glide için yükleme seçenekleri oluşturuluyor
    val options = RequestOptions()
        // Yüklenirken gösterilecek geçici görsel (circularProgressDrawable) belirleniyor
        .placeholder(circularProgressDrawable)
        // Hata durumunda gösterilecek görsel (örneğin, yükleme başarısız olursa)
        .error(R.drawable.ic_launcher_background)

    // Glide kullanılarak URL'den resim yükleniyor
    Glide.with(context)                     // Glide için bağlam (context) belirtiliyor
        .setDefaultRequestOptions(options)   // Yükleme seçenekleri uygulanıyor
        .load(Url)                           // Resmi yükle (URL'den)
        .fitCenter()                         // Resmi, ImageView'ın içine tam sığacak şekilde ölçeklendir
        .into(this)                          // Yüklenen resmi bu ImageView'da göster
}

// Yüklenme sırasında bir döner çubuk animasyonu (CircularProgressDrawable) oluşturmak için fonksiyon
fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    // CircularProgressDrawable nesnesi oluşturuluyor
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f    // Döner çubuğun kalınlığı (çizgi genişliği)
        centerRadius = 40f  // Döner çubuğun merkezi yarıçapı
        start()             // Animasyonu başlat
    }
}
/*@BindingAdapter("android:downloadUrl")
    fun downloadImage(imageView:ImageView,Url: String){
        imageView.DownloadUrl(Url, placeHolderProgressBar(imageView.context))

    }*/
@BindingAdapter("android:downloadUrl")
fun downloadImage(imageView: ImageView, Url: String?) {
    /*Url?.let { // Url null değilse işlemi yapar
        //url verisi tıklanma anında hemen gelmeyebilir o yüzden let bloğu gerekli veri geldiği anda da hata görünür.
        imageView.DownloadUrl(it, placeHolderProgressBar(imageView.context))
    }*/
    if(Url==null){
        println("url null oldu")
    }
    else{
        println("url nulldan çıktı")
        imageView.DownloadUrl(Url, placeHolderProgressBar(imageView.context))

    }
}



