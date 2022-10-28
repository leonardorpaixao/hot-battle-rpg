buildscript {

    // FIXME: Repository declaration should be moved to settings.gradle file to work with gradle 7.4.2+
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
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.linter) apply true
    alias(libs.plugins.kotlin.android) apply false
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}