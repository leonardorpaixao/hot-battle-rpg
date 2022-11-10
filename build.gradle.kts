buildscript {

    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
        classpath("org.jetbrains.compose:compose-gradle-plugin:1.2.0-rc01")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.linter) apply true
    alias(libs.plugins.kotlin.android) apply false
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}