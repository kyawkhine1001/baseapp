plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("maven-publish")
    id("kotlin-parcelize")
}
android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        multiDexEnabled = true
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(Signings.BUILD.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

    }
    compileOptions {
        sourceCompatibility  = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        this.jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    installUILibraries()
//    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.3.0")
//    implementation("com.google.android.material:material:1.3.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
//    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
//    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
//    )
//    testImplementation("junit:junit:4.+")
//    androidTestImplementation("androidx.test.ext:junit:1.1.2")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // Networking
    installRetrofit()
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.okhttp3:okhttp:3.8.1")
//    implementation("com.squareup.okhttp3:logging-interceptor:3.8.1")

    // Rx
    installRxJava()
//    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
//    implementation("com.squareup.retrofit2:adapter-rxjava:2.4.0")
//    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
//    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    //Architecture Component
    installAndroidXArchitecture()
//    implementation("android.arch.lifecycle:extensions:1.0.0")
//    implementation("com.android.support.constraint:constraint-layout:2.0.4")
//    kapt("android.arch.lifecycle:compiler:1.0.0")
//    testImplementation("android.arch.core:core-testing:1.0.0")

    //Glide
    installGlide()
//    implementation("com.github.bumptech.glide:glide:4.13.0")
//    kapt("com.github.bumptech.glide:compiler:4.13.0")

//    //Crop Image View
//    implementation("com.isseiaoki:simplecropview:1.1.8")
//    //Zoomable Image View
//    implementation("com.github.chrisbanes:PhotoView:2.3.0")

    installGsonJson()
    installGMSService()
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
//
//    //Gson
//    implementation("com.google.code.gson:gson:2.9.0")


    //Database
    installRoomDatabase()

    //DataStore
    installDataStore()
//    implementation("androidx.datastore:datastore-core:1.0.0")
//    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Coroutine
    installCoroutine()
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")
//    implementation("com.github.satoshun.coroutine.autodispose:autodispose:0.3.1")
//    implementation("androidx.work:work-runtime-ktx:2.7.1")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")

    // Koin
//    implementation "org.koin:koin-android-viewmodel:$koin_version"
//    implementation "org.koin:koin-android:$koin_version"
//    testImplementation "org.koin:koin-test:$koin_version"
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            register<MavenPublication>("release") {
                // Applies the component for the release build variant.
//                from(components["release"])

                // You can then customize attributes of the publication as shown below.
                groupId = "com.kkk.mylibrary"
                artifactId = "mylibrary"
                version = "1.0"
            }
        }
    }
}