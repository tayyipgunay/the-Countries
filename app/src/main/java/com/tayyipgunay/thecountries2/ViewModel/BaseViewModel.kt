package com.tayyipgunay.thecountries2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

// BaseViewModel: Tüm ViewModel'ler için temel sınıf olarak tanımlanmış bir soyut (abstract) sınıf
// AndroidViewModel sınıfını genişletir ve CoroutineScope arayüzünü uygular.
// AndroidViewModel, uygulama bağlamını (Application) alır ve ViewModel'in yaşam döngüsünü yönetir.
abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    // job: Coroutine'leri kontrol etmek ve yönetmek için kullanılan bir Job nesnesi.
    // Bu Job, coroutine'lerin iptal edilmesini ve temizlenmesini sağlar.
    private val job = Job()
    protected open val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("Global Exception Caught: ${throwable.message}")
    }

    // coroutineContext: CoroutineScope'un bir özelliği.
    // Coroutine'lerin hangi bağlamda çalışacağını belirler.
    // Bu bağlam, job ve Dispatchers.Main'in birleşimidir.
    // job: Coroutine'lerin iptalini kontrol eder.
    // Dispatchers.Main: Coroutine'lerin ana iş parçacığında (UI thread) çalışmasını sağlar.
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    // job: Arka planda çalışan coroutine'lerin yönetimi için.
    // Dispatchers.Main: Coroutine'lerin ana iş parçacığında çalışması için, örneğin UI güncellemeleri için.

    // onCleared: ViewModel yok edildiğinde (örneğin, Activity veya Fragment sona erdiğinde) çağrılır.
    // Bu metod, başlatılan tüm coroutine'leri iptal ederek bellek sızıntılarını önler.
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Tüm coroutine'leri iptal eder ve kaynakları temizler.
    }
}
// Android Jetpack Kütüphanesinde Tanımlı
/*open class AndroidViewModel(val application: Application) : ViewModel() {

    // Uygulama bağlamını almak için bir metod sağlar
    fun getApplication(): Application {
        return application
    }
}*/
/*Farklar
Yaşam Döngüsü:

applicationContext: Uygulamanın tüm yaşam döngüsünü kapsar.
activityContext: Belirli bir aktivitenin yaşam döngüsüne bağlıdır.
Kapsam:

applicationContext: Uygulama genelinde geçerlidir.
activityContext: Yalnızca o aktivite için geçerlidir.
Kullanım Durumları:

applicationContext: Uygulama boyunca kullanılacak, uzun süreli işlemler için uygundur.
activityContext: Kullanıcı arayüzüyle ilgili veya aktiviteye özgü işlemler için kullanılır.
*/
