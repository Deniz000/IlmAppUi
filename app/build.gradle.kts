plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.ilmapp"
    compileSdk = 35
    // SDK 34, Android 13 için uygun olabilir. Android 12 için 31 kullanabilirsiniz.

    defaultConfig {
        applicationId = "com.example.ilmapp"
        minSdk = 23 // Minimum SDK, Android 6.0 (Marshmallow)
        targetSdk = 35 // Android 13'e uygun olmalı.
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true // ViewBinding'i etkinleştir
    }
    ndkVersion = rootProject.extra["ndkVersion"] as String

    // Eğer Android 12'ye özgü özellikler veya ayarlamalar gerekiyorsa, burada ekleyebilirsiniz.
    // Örneğin, "Splash Screen" desteği eklemek için:
//    splashScreen {
//        // Android 12'de SplashScreen için temel yapılandırma
//        iconDrawable = "@drawable/splash_icon"
//        backgroundColor = "#FFFFFF" // Splash ekran arka plan rengi
//    }
}

dependencies {
    implementation(libs.androidx.core.ktx) // Kotlin Extensions
    implementation(libs.androidx.appcompat) // AppCompat kütüphanesi
    implementation(libs.material) // Material Components
    implementation(libs.androidx.constraintlayout) // ConstraintLayout
    implementation(libs.androidx.lifecycle.livedata.ktx) // LiveData Kotlin Extensions
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel Kotlin Extensions
    implementation(libs.androidx.navigation.fragment.ktx) // Navigation Component
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.annotation) // Navigation UI Extensions
    testImplementation(libs.junit) // JUnit test framework
    androidTestImplementation(libs.androidx.junit) // JUnit test framework for Android
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing
    implementation(libs.androidx.core.splashscreen) // Android 12 SplashScreen
    implementation (libs.circleimageview)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.library)
    implementation(libs.jwtdecode.v202)
}
