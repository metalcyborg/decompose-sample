plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

group "com.example"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.7.2")
}

android {
    namespace = "com.example.android"

    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
   compileOptions {
       sourceCompatibility = JavaVersion.VERSION_1_8
       targetCompatibility = JavaVersion.VERSION_1_8
   }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
