plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jmailen.kotlinter")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.paixao.labs.myapplication"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc02"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf("-Xjvm-default=enable")
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

    // Compose
    implementation(libs.compose.compiler)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.material.core)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.preview)
    implementation(libs.compose.animation)
    implementation(libs.compose.activity)

    // Compose Debug & Testing
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.debug.manifest)
    debugImplementation(libs.compose.debug.tooling)

    // AndroidX
    implementation(libs.androidX.core.ktx)
    implementation(libs.androidX.lifecycle.runtime)

    // Testing
    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.mockk)

    // Ui Testing
    androidTestImplementation(libs.uiTesting.junit)
    androidTestImplementation(libs.uiTesting.espresso)

    // Hilt
    implementation(libs.hilt.core)
    kapt(libs.hilt.kapt)

    //navigation

    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transition)
    implementation(libs.voyager.hilt)
    implementation(libs.voyager.androidX)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.coroutines)
    implementation(libs.firebase.auth)
    implementation(libs.google.playservices.auth)
}

kapt {
    correctErrorTypes = true
}