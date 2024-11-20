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
    buildFeatures {
        viewBinding = true
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

    //
    implementation("com.github.lzyzsd:circleprogress:1.1.0") //for tracker donut progress circle
    implementation(libs.appcompat)
    implementation(libs.material)

    // for Recycle & card view
    implementation(libs.cardview)
    implementation(libs.com.github.bumptech.glide.glide2)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.preference)
    // Glide v4 uses this new annotation processor -- see https://bumptech.github.io/glide/doc/generatedapi.html
    annotationProcessor(libs.compiler)
    implementation(libs.constraintlayout.v213)
    implementation(libs.appcompat);

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)

    // Android Instrumented Testing
    androidTestImplementation(libs.junit.v121)
    androidTestImplementation(libs.espresso.core.v361)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockito.core)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Add the dependency for the Firebase Authentication library
    implementation(libs.firebase.auth.ktx)

    // Also add the dependency for the Google Play services library and specify its version
    implementation(libs.play.services.auth)

    implementation(libs.firebase.analytics)

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database-ktx:20.2.0")

    // Optional: Firebase Firestore
    implementation("com.google.firebase:firebase-firestore-ktx:24.5.0")
}