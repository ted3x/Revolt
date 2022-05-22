plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.6.10-1.0.4"
}

android {
    defaultConfig {
        minSdk = 23
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(libs.androidx.core)
    implementation(libs.adapter.delegates.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit.retrofit)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.moshi.core)
    ksp(libs.moshi.ksp)
    implementation(libs.glide.glide)
    annotationProcessor(libs.glide.kapt)
}