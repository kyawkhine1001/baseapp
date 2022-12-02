import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //std lib
    val kotlinStdJDKLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:1.7.10"

    //android ui
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    private val material = "com.google.android.material:material:${Versions.material}"
    private val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"
    private val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    private val slidingCarousel =
        "com.github.akshaaatt:Sliding-Carousel:${Versions.slidingCarousel}"
    private val simpleCropView = "com.isseiaoki:simplecropview:${Versions.simpleCropView}"
    private val chrisbanesPhotoView = "com.github.chrisbanes:PhotoView:${Versions.chrisbanesPhotoView}"


    //retrofit
    private val retrofitConverterJson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    //RxJava
    private val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    private val rxjavaAdapter =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.rxjavaAdapter}"
    private val retrofitAdapterRxjava =
        "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofitAdapterRxjava}"
    private val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    //Architecture
    val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidXArch}"
    val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXArch}"
    val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidXArch}"
    val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXArch}"
    val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidXArch}"
    //Paging
    val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"

    //Room Database
    val roomRuntime = "androidx.room:room-runtime:${Versions.roomDatabase}"
    val roomRxJava = "androidx.room:room-rxjava2:${Versions.roomDatabase}"
    val roomKtx = "androidx.room:room-ktx:${Versions.roomDatabase}"
    val roomPaging = "androidx.room:room-paging:${Versions.roomDatabase}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.roomDatabase}"

    //Koin
    val koin = "io.insert-koin:koin-android:${Versions.koin}"

    //Hilt
    val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    //Coroutine
    val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    val coroutineAutoDispose =
        "com.github.satoshun.coroutine.autodispose:autodispose:${Versions.coroutineAutoDispose}"
    val work = "androidx.work:work-runtime-ktx:${Versions.work}"

    //DataStore
    val dataStoreCore = "androidx.datastore:datastore-core:${Versions.dataStore}"
    val dataStorePreferences = "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    //Glide
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //Kotlin Serialization
    val kotlinSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerializationJson}"

    //Gson
    val gson = "com.google.code.gson:gson:${Versions.gson}"

    //Google Play Service
    val gmsPlayService = "com.google.android.gms:play-services-base:${Versions.gmsPlayService}"

    //test libs
    private val junitTest = "junit:junit:${Versions.junitTest}"
    private val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"
    private val mockitoCoreTest = "org.mockito:mockito-core:${Versions.mockitoTest}"
    private val archCoreTest = "android.arch.core:core-testing:${Versions.archCoreTesting}"

    //Android Test
    private val extJUnitTest = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCoreTest = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private val mockitoAndroidTest = "org.mockito:mockito-android:${Versions.mockitoTest}"

    object AppLibraries {
        val uiLibraries = arrayListOf<String>().apply {
            add(kotlinStdLib)
            add(coreKtx)
            add(appcompat)
            add(constraintLayout)
            add(material)
            add(legacySupportV4)
            add(navigationFragment)
            add(navigationUI)
            add(slidingCarousel)
            add(simpleCropView)
            add(chrisbanesPhotoView)
        }
        val androidXArchitecture = arrayListOf<String>().apply {
            add(extensions)
            add(lifecycleJava8)
            add(runtimeKtx)
            add(viewModelKtx)
            add(liveDataKtx)
            add(paging)
        }
        val retrofitLibrary = arrayListOf<String>().apply {
            add(retrofitConverterJson)
            add(retrofit)
            add(okhttp)
            add(okhttpInterceptor)
        }
        val rxJava = arrayListOf<String>().apply {
            add(rxjava)
            add(rxjavaAdapter)
            add(retrofitAdapterRxjava)
            add(rxAndroid)
        }
        val roomDatabase = arrayListOf<String>().apply {
            add(roomRuntime)
            add(roomRxJava)
            add(roomKtx)
            add(roomPaging)
//            add(roomCompiler)
        }
        val coroutineLibrary = arrayListOf<String>().apply {
            add(coroutine)
            add(coroutineAutoDispose)
            add(work)
        }
        val dataStore = arrayListOf<String>().apply {
            add(dataStoreCore)
            add(dataStorePreferences)
        }
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junitTest)
        add(coroutineTest)
        add(mockitoCoreTest)
        add(archCoreTest)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnitTest)
        add(espressoCoreTest)
        add(mockitoAndroidTest)
    }

}

fun DependencyHandler.installUILibraries() {
    implementation(AppDependencies.AppLibraries.uiLibraries)
}

fun DependencyHandler.installRetrofit() {
    implementation(AppDependencies.AppLibraries.retrofitLibrary)
}

fun DependencyHandler.installRxJava() {
    implementation(AppDependencies.AppLibraries.rxJava)
}

fun DependencyHandler.installAndroidXArchitecture() {
    implementation(AppDependencies.AppLibraries.androidXArchitecture)
}

fun DependencyHandler.installRoomDatabase() {
    implementation(AppDependencies.AppLibraries.roomDatabase)
    kapt(AppDependencies.roomCompiler)
}

fun DependencyHandler.installKoin() {
    implementation(AppDependencies.koin)
}

fun DependencyHandler.installHilt() {
    implementation(AppDependencies.hilt)
    kapt(AppDependencies.hiltAndroidCompiler)
    kapt(AppDependencies.hiltCompiler)
}

fun DependencyHandler.installCoroutine() {
    implementation(AppDependencies.AppLibraries.coroutineLibrary)
}

fun DependencyHandler.installDataStore() {
    implementation(AppDependencies.AppLibraries.dataStore)
}
fun DependencyHandler.installGlide() {
    implementation(AppDependencies.glide)
    kapt(AppDependencies.glideCompiler)
}
fun DependencyHandler.installGsonJson(){
    implementation(AppDependencies.kotlinSerializationJson)
    implementation(AppDependencies.gson)
}
fun DependencyHandler.installGMSService(){
    implementation(AppDependencies.gmsPlayService)
}


fun DependencyHandler.installTest() {
    testImplementation(AppDependencies.testLibraries)
}

fun DependencyHandler.installAndroidTest() {
    androidTestImplementation(AppDependencies.androidTestLibraries)

}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.kapt(dependency: String) {
    add("kapt", dependency)
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}