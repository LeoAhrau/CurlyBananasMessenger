plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
    id("com.google.gms.google-services")
   // id("kotlin-android")
    //id("kotlin-kapt")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.curlybananasmessenger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.curlybananasmessenger"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {
    implementation("androidx.activity:activity-ktx:1.8.2")//Room
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")//Room
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("com.google.android.datatransport:transport-runtime:3.3.0") //Room
    ksp("androidx.room:room-compiler:2.6.1")  // Room
    //kapt("androidx.room:room-compiler:2.6.1")  // Room
    implementation("androidx.room:room-common:2.6.1")//Room
    implementation("androidx.room:room-ktx:2.6.1")// Room
    implementation("androidx.room:room-runtime:2.6.1") // Room
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")  // Kotlin Coroutines

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.1")
    implementation("com.google.firebase:firebase-auth")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("com.google.android.material:material:1.13.0-alpha01")
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database-ktx:20.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}
