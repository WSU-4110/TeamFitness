plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
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

        // Test runner for instrumentation tests
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
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

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true // Correct Kotlin DSL property
        }
    }
}

dependencies {
    // **Core Libraries**
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.coordinatorlayout)
    implementation(libs.cardview)
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation(libs.testng)
    implementation(libs.espresso.core)
    implementation(libs.espresso.intents)

    // **Unit Testing** Dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testImplementation("junit:junit:4.13.2") // JUnit for unit tests
    testImplementation("androidx.test:core:1.5.0") // AndroidX Test Core
    testImplementation("org.robolectric:robolectric:4.10.3") // Robolectric for JVM-based Android testing
    testImplementation("org.mockito:mockito-core:5.5.0") // Mockito for mocking dependencies

    // **Instrumentation Testing** Dependencies
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // JUnit extensions for instrumentation
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso for UI tests

    // **Firebase Libraries**
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)

    // **Circle Progress Library**
    implementation("com.github.lzyzsd:circleprogress:1.1.0")

    // **Glide**
    implementation(libs.com.github.bumptech.glide.glide2)
    annotationProcessor(libs.compiler)
}
