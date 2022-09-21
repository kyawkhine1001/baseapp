import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    id("com.google.dagger.hilt.android") version "2.43.2" apply false
}

repositories {
    mavenCentral()
}