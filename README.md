# TheCountries2 Uygulaması

Bu proje, **MVVM (Model-View-ViewModel)** mimarisi ile geliştirilmiş bir **ülke bilgileri** uygulamasıdır. Kullanıcılar farklı ülkelerin bilgilerini listeleyebilir.

## Özellikler
- **Ülke Listesi:** API'den alınan ülkeleri listeleme.
- **Detay Sayfası:** Seçilen ülkeye ait detay bilgilerini görüntüleme.
- **Room Database:** Verileri önbelleğe alarak çevrimdışı erişim sağlama.
- **Retrofit Kullanımı:** API çağrıları için.
- **Picasso / Glide Kullanımı:** Görselleri yüklemek için.

## Kullanılan Teknolojiler
- **Kotlin**: Android geliştirme dili.
- **MVVM**: Uygulama mimarisi.
- **Retrofit**: HTTP istekleri için.
- **Room Database**: Yerel veri saklama.
- **LiveData & ViewModel**: UI güncellemeleri için.
- **RecyclerView**: Listeleme için.

## Proje Yapısı

### **Ana Modüller**
- **adapter**: RecyclerView için adaptör içerir.
- **model**: Country veri modelini içerir.
- **service**: API isteklerini yöneten Retrofit entegrasyonu.
- **Util**: Yardımcı sınıflar.
- **View**: Kullanıcı arayüzü bileşenleri (Activity/Fragment).
- **ViewModel**: UI ile veri kaynağı arasındaki köprü.

## Kullanım
1. **Uygulamayı başlatın.**
2. **Ülkeler listesinden bir ülke seçin.**
3. **Seçilen ülkenin detay sayfasına yönlendirilin.**

## Kurulum
1. **Projeyi klonlayın:**
   ```sh
   git clone https://github.com/kullaniciadi/TheCountries2.git
   ```
2. **Android Studio ile açın.**
3. **Firebase, API veya gerekli diğer servislerin yapılandırmasını tamamlayın.**
4. **Cihaz veya emülatör seçerek çalıştırın.**

## Katkıda Bulunma
Projeye katkıda bulunmak için bir pull request oluşturabilir veya issue açabilirsiniz.

## Lisans
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için LICENSE dosyasına bakınız.

![WhatsApp Görsel 2025-02-26 saat 16 32 07_9d5f8b49](https://github.com/user-attachments/assets/128c407b-9074-4440-b9fd-2cde38660a80)
