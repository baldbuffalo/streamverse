plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.streamverse.app"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.streamverse.app"
        minSdk = 23
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            storeFile     = file("streamverse.jks")
            storePassword = "streamverse123"
            keyAlias      = "streamverse"
            keyPassword   = "streamverse123"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled   = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_26
        targetCompatibility = JavaVersion.VERSION_26
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2026.05.01")
    implementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime")

    // Jetpack Compose for TV
    implementation("androidx.tv:tv-foundation:1.0.0")
    implementation("androidx.tv:tv-material:1.0.0")

    // Core
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0-rc01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0-rc01")

     // Google Sign-In (system account picker via Play Services)
     implementation("com.google.android.gms:play-services-auth:21.6.0")

    // Firebase — Auth (bridges Google Sign-In to a stable per-user UID)
    // and Firestore (stores "My List" + watch history, synced per Google
    // account). Using base artifacts, not -ktx: Google now folds Kotlin
    // extensions into the main modules and is deprecating the -ktx ones.
    implementation(platform("com.google.firebase:firebase-bom:34.15.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.10.0-alpha05")

    // Image loading
    implementation("io.coil-kt.coil3:coil-compose:3.5.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.5.0")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
