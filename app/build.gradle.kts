plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'org.jmailen.kotlinter'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
    id 'com.google.gms.google-services'
}

android {
    compileSdk 33

    defaultConfig {
        applicationId "com.paixao.labs.myapplication"
        minSdk 26
        targetSdk 33
        versionCode 1
        versionName "1.0"
    }
    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion composeCompiler
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs += [
                '-Xjvm-default=enable'
        ]
    }
    testOptions {
        unitTests.all {
            useJUnitPlatform()
        }
    }
}

dependencies {

    implementation "androidx.compose.compiler:compiler:$compose_version_compiler"
    implementation "androidx.compose.runtime:runtime:$compose_version"
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.material:material:$compose_version"
    implementation "androidx.compose.material:material-icons-core:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation "androidx.compose.animation:animation:$compose_version"

    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_version"
    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"

    implementation 'androidx.activity:activity-compose:1.6.0'

    implementation 'androidx.core:core-ktx:1.6.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.4.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-compiler:$hilt_version"

    //navigation

    implementation "cafe.adriel.voyager:voyager-navigator:$voyager_version"
    implementation "cafe.adriel.voyager:voyager-transitions:$voyager_version"
    implementation "cafe.adriel.voyager:voyager-hilt:$voyager_version"
    implementation "cafe.adriel.voyager:voyager-androidx:$voyager_version"


    implementation platform('com.google.firebase:firebase-bom:30.5.0')
    implementation 'com.google.firebase:firebase-firestore-ktx'
    implementation 'com.google.firebase:firebase-database-ktx'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4'
    implementation 'com.google.firebase:firebase-auth-ktx'
    implementation 'com.google.android.gms:play-services-auth:20.3.0'

    testImplementation "io.mockk:mockk:1.13.2"

}

kapt {
    correctErrorTypes true
}