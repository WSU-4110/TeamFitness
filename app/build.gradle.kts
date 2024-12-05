plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34
    testNamespace = "com.example.myapplication.test"

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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.github.lzyzsd:circleprogress:1.1.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.coordinatorlayout)

    // RecyclerView and CardView
    implementation(libs.cardview)
    implementation(libs.com.github.bumptech.glide.glide2)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database)
    implementation("com.google.firebase:firebase-database-ktx:20.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.5.0")
    implementation(libs.play.services.auth)
    implementation(libs.firebase.analytics)
    testImplementation(libs.ext.junit)

    // Annotation Processor
    annotationProcessor(libs.compiler)

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.robolectric:robolectric:4.10")
    testImplementation("androidx.test:core:1.5.0")


    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.0")
    debugImplementation("androidx.fragment:fragment-testing:1.5.7")
    implementation("androidx.preference:preference:1.2.1")

    implementation("com.google.firebase:firebase-auth:21.0.8")
    testImplementation("com.google.firebase:firebase-auth:21.0.8")
    implementation ("com.google.android.material:material:1.9.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    androidTestImplementation("androidx.test:core:1.3.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // Existing dependencies in your original file
    implementation("androidx.annotation:annotation:1.5.0")
    implementation("androidx.test.espresso:espresso-idling-resource:3.5.1")
}

// Remove circular dependency by ensuring tests run as part of "check"
tasks.named("check") {
    dependsOn("testDebugUnitTest")
}

// Ensure tests are executed
tasks.withType<Test> {
    ignoreFailures = false
}