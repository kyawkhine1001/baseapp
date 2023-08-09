plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
//    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
}
android {
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "com.kkk.baseapp"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        multiDexEnabled = true
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    signingConfigs {
//        getByName(Signings.BUILD.DEBUG) {
//            storeFile = File(rootDir, "debug.keystore")
//            storePassword = Signings.DEBUG.storePassword
//            keyAlias = Signings.DEBUG.keyAlias
//            keyPassword = Signings.DEBUG.keyPassword
//
//        }
//        getByName(Signings.BUILD.RELEASE) {
//            storeFile = File(rootDir, "debug.keystore")
//            storePassword = Signings.RELEASE.storePassword
//            keyAlias = Signings.RELEASE.keyAlias
//            keyPassword = Signings.RELEASE.keyPassword
//        }
    }

    buildTypes {
        getByName(Signings.BUILD.DEBUG) {
            isTestCoverageEnabled = true
        }

        getByName(Signings.BUILD.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions += (AppConfig.dimension)
    productFlavors {
        create("dev") {
            dimension = AppConfig.dimension
            buildConfigField("String","API_BASE_URL","\"${Development.API.baseUrl}\"")
            applicationIdSuffix = ".dev"
        }
        create("prod") {
            dimension = AppConfig.dimension
            buildConfigField("String","API_BASE_URL","\"${Production.API.baseUrl}\"")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
//    implementation(AppDependencies.appLibraries)
//    implementation(AppDependencies.AppLibraries.uiLibraries)
    installUILibraries()

//    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.5.1")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(project("path" to ":mylibrary"))

//    implementation 'com.github.KyawKyawKhing:SharedBase:0.0.1'
//    implementation 'com.github.KyawKyawKhing:SharedBase:0.1.2'
//    implementation project(path: ':sharedbase')
//    implementation 'com.github.kyawkhine1001:baseapp:0.0.2'

//    implementation("com.google.android.material:material:1.6.1")
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
//    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
//    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    //retrofit
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")

//    implementation(AppDependencies.AppLibraries.retrofitLibrary)
    installRetrofit()

    //RxJava
//    implementation(AppDependencies.AppLibraries.rxJava)
    installRxJava()
//    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
//    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
//    implementation("com.squareup.retrofit2:adapter-rxjava:2.4.0")
//    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    //Architecture Component
//    implementation(AppDependencies.AppLibraries.androidXArchitecture)
    installAndroidXArchitecture()
//    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
//    kapt 'android.arch.lifecycle:compiler:1.1.1'

    //Room Database
//    implementation(AppDependencies.AppLibraries.roomDatabase)
    installRoomDatabase()
//    implementation("androidx.room:room-runtime:2.4.3")
//    implementation("androidx.room:room-rxjava2:2.4.3")
//    annotationProcessor("androidx.room:room-compiler:2.4.3")
//    kapt("androidx.room:room-compiler:2.4.3")

    //Koin
    installKoin()
//    implementation("io.insert-koin:koin-android:3.1.4")

    //Dagger - Hilt
    installHilt()
//    implementation("com.google.dagger:hilt-android:2.43.2")
//    kapt("com.google.dagger:hilt-android-compiler:2.43.2")
//    kapt("com.google.dagger:hilt-compiler:2.43.2")


//    //Material
//    implementation("com.google.android.material:material:1.6.1")

    //viewpager
//    implementation 'com.github.devlight:infinitecycleviewpager:1.0.2'
//    implementation 'com.synnapps:carouselview:0.1.5'
//    implementation("com.github.akshaaatt:Sliding-Carousel:1.0.4")

//    //Glide
//    implementation 'com.github.bumptech.glide:glide:4.13.0'
//    annotationProcessor 'com.github.bumptech.glide:compiler:4.13.0'


    //Coroutine
    installCoroutine()
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")
//    implementation("com.github.satoshun.coroutine.autodispose:autodispose:0.3.1")
//    implementation("androidx.work:work-runtime-ktx:2.7.1")

    //DataStore
    installDataStore()
//    implementation("androidx.datastore:datastore-core:1.0.0")
//    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Test
    installTest()
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")
//    testImplementation("junit:junit:4.13.2")
//    testImplementation("org.mockito:mockito-core:3.6.28")
//    testImplementation("android.arch.core:core-testing:1.1.1")

    //Android Test
    installAndroidTest()
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    androidTestImplementation("org.mockito:mockito-android:3.6.28")



}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
