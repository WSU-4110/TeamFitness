import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.coordinatorlayout)

    // Tracker donut progress circle
    implementation("com.github.lzyzsd:circleprogress:1.1.0")

    // RecyclerView & CardView
    implementation(libs.cardview)
    implementation(libs.com.github.bumptech.glide.glide2)

    // Firebase
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.preference)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.analytics)
    implementation("com.google.firebase:firebase-database-ktx:20.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.5.0")
    testImplementation(libs.ext.junit)
    testImplementation(libs.ext.junit) // Optional Firestore

    // Annotation processor for Glide
    annotationProcessor(libs.compiler)

    // AndroidX Testing dependencies for AndroidJUnit4 (instrumented tests)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Robolectric
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("androidx.test:core:1.4.0")
}

// Remove circular dependency by ensuring tests run as part of "check"
tasks.named("check") {
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")
}

// Ensure tests are executed
tasks.withType<Test> {
    ignoreFailures = false
}


