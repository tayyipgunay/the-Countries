plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // id("androidx.navigation.safeargs.kotlin") version "2.8.0" // Safe Args plugin
    id("org.jetbrains.kotlin.kapt") version "1.9.0" // Kapt plugin
    //id("androidx.navigation.safeargs") //version "2.8.0" // Safe Args plugin
    id("androidx.navigation.safeargs")

}



android {
    namespace = "com.tayyipgunay.thecountries2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tayyipgunay.thecountries2"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.ui.desktop)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    val lifeCycleExtensionVersion = "1.1.1"
    val supportVersion = "28.0.0"
    val glideVersion = "4.9.0"
    val rxJavaVersion = "2.1.1"
    val navVersion = "2.2.1"
    val preferencesVersion = "1.1.0"


   /* val roomVersion = "2.6.1"
    val coroutinesVersion = "1.5.2"
    val retrofitVersion = "2.3.0"
    retrofit coroutine uyumsuzluğu oldu
*/


      val roomVersion = "2.6.1"
      val retrofitVersion = "2.9.0"
    val coroutinesVersion = "1.6.4"

    //val coroutinesVersion = "1.5.2"




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.lifecycle.extensions)


    /* implementation ("androidx.room:room-runtime:$roomVersion")


    kapt ("androidx.room:room-compiler:$roomVersion")

    implementation ("androidx.room:room-ktx:$roomVersion")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")*/ // önceki bu


    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")







    implementation("androidx.legacy:legacy-support-v4:1.0.0")


    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")

    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation("com.google.android.material:material:1.1.0")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

    implementation("androidx.room:room-rxjava2:$roomVersion")//final


    implementation("io.reactivex.rxjava2:rxjava:$rxJavaVersion")

    implementation("io.reactivex.rxjava2:rxandroid:$rxJavaVersion")



    implementation("androidx.preference:preference:$preferencesVersion")



    implementation("com.github.bumptech.glide:glide:4.12.0")

    kapt("com.github.bumptech.glide:compiler:4.12.0")
    // Eğer kapt kullanıyorsanız
    implementation("androidx.palette:palette:1.0.0")


    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    /*val roomVersion = "2.5.2" // En güncel Room sürümü
    val coroutinesVersion = "1.6.4" // En güncel Coroutines sürümü


    val lifecycleVersion = "2.6.1" // En güncel Lifecycle sürümü
    val navVersion = "2.5.3" // En güncel Navigation sürümü
    val retrofitVersion = "2.9.0" // En güncel Retrofit sürümü
    val glideVersion = "4.12.0" // Glide için stabil sürüm
    val rxJavaVersion = "2.2.21" // RxJava2 en güncel stabil sürüm
    val preferencesVersion = "1.2.0" // En güncel Preferences sürümü
    val materialVersion = "1.9.0" // En güncel Material Design sürümü


        // AndroidX Core
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)

        // Room
        implementation("androidx.room:room-runtime:$roomVersion")
        kapt("androidx.room:room-compiler:$roomVersion")
        implementation("androidx.room:room-ktx:$roomVersion")

        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

        // Navigation
        implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
        implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

        // Material Design
        implementation("com.google.android.material:material:$materialVersion")

        // Retrofit ve Gson Converter
        implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
        implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
        implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

        // RxJava ve RxAndroid
        implementation("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
        implementation("io.reactivex.rxjava2:rxandroid:2.1.1") // RxAndroid için en stabil sürüm

        // AndroidX Preferences
        implementation("androidx.preference:preference:$preferencesVersion")

        // Glide
        implementation("com.github.bumptech.glide:glide:$glideVersion")
        kapt("com.github.bumptech.glide:compiler:$glideVersion")

        // Palette
        implementation("androidx.palette:palette:1.0.0")

        // Lifecycle ViewModel ve LiveData
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

        // Legacy Support
        implementation("androidx.legacy:legacy-support-v4:1.0.0")

        // Test Kütüphaneleri
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
*/

}

