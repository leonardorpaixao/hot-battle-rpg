buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
        classpath("org.jetbrains.compose:compose-gradle-plugin:1.2.0-rc01")
    }
}
plugins {
    id("com.android.application") version ("7.2.2") apply false
    id("com.android.library") version ("7.2.2") apply false
    id("org.jetbrains.kotlin.android") version ("1.6.10") apply false
    id("org.jmailen.kotlinter") version ("3.3.0") apply true
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}