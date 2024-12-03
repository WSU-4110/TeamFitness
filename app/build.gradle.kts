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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

// Run all Unit and Instrumented Tests
tasks.register<Test>("runSelectedTests") {
    useJUnitPlatform()
    testLogging {
        events("started", "passed", "skipped", "failed", "standardOut", "standardError")
        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
    }
    filter {
        // Add specific tests from androidTest
        includeTestsMatching("com.example.myapplication.progress.ProgressViewModelTest")
        includeTestsMatching("com.example.myapplication.WorkoutAdapterTest")
        includeTestsMatching("com.example.myapplication.WorkoutTest")
    }
}

tasks.named("build") {
    finalizedBy("runSelectedTests")
}

dependencies {
    // Core Libraries
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

    // Firebase Dependencies
    implementation("com.github.lzyzsd:circleprogress:1.1.0")
    implementation(libs.cardview)
    implementation(libs.com.github.bumptech.glide.glide2)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.preference)
    annotationProcessor(libs.compiler)
    implementation(libs.constraintlayout.v213)

    // Unit Tests (src/test/java)
    testImplementation("junit:junit:4.13.2")

    // Instrumented Tests (src/androidTest/java)
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
}
