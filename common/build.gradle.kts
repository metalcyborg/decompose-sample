plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("kotlin-parcelize")
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
       compilations.all {
           kotlinOptions.jvmTarget = "17"
       }
    }
    sourceSets {
        val ktorVersion = "2.3.2"
        val decomposeVersion = "2.0.0"

        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api("com.arkivanov.decompose:decompose:$decomposeVersion")
                api("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
                implementation("androidx.compose.ui:ui-graphics:1.4.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.1")
                implementation("io.ktor:ktor-client-java:$ktorVersion")
            }
        }
        val desktopTest by getting
    }
}

android {
    namespace = "com.example.common"

    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
   compileOptions {
       sourceCompatibility = JavaVersion.VERSION_17
       targetCompatibility = JavaVersion.VERSION_17
   }
}
