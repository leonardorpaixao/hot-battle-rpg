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

    implementation("androidx.compose.compiler:compiler:1.3.2")
    implementation("androidx.compose.runtime:runtime:1.3.0-rc01")
    implementation("androidx.compose.ui:ui:1.3.0-rc01")
    implementation("androidx.compose.material:material:1.3.0-rc01")
    implementation("androidx.compose.material:material-icons-core:1.3.0-rc01")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.0-rc01")
    implementation("androidx.compose.animation:animation:1.3.0-rc01")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.0-rc01")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.0-rc01")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.0-rc01")

    implementation("androidx.activity:activity-compose:1.6.0")

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-compiler:2.40.5")

    //navigation

    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc02")
    implementation("cafe.adriel.voyager:voyager-transitions:1.0.0-rc02")
    implementation("cafe.adriel.voyager:voyager-hilt:1.0.0-rc02")
    implementation("cafe.adriel.voyager:voyager-androidx:1.0.0-rc02")


    implementation(platform("com.google.firebase:firebase-bom:30.5.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.3.0")

    testImplementation("io.mockk:mockk:1.13.2")

}

kapt {
    correctErrorTypes = true
}