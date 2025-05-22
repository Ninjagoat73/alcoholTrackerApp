plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.kotlin.plugin.compose")

}

kotlin {

// Target declarations - add or remove as needed below. These define
// which platforms this KMP module supports.
// See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.example.shared"
        compileSdk = 35
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

// For iOS targets, this is also where you should
// configure native binary output. For more information, see:
// https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

// A step-by-step guide on how to include this library in an XCode
// project can be found here:
// https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "sharedKit"


// Source set declarations.
// Declaring a target automatically creates a source set with the same name. By default, the
// Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
// common to share sources between related targets.
// See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.kodein.di:kodein-di:7.20.0")
                implementation("org.kodein.di:kodein-di-framework-compose:7.20.0")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.10")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

                implementation("org.jetbrains.compose.runtime:runtime:1.7.3")
                implementation("org.jetbrains.compose.foundation:foundation:1.5.11")
                implementation("org.jetbrains.compose.material:material:1.5.11")
                implementation("org.jetbrains.compose.animation:animation:1.7.3")
                implementation("org.jetbrains.compose.ui:ui-tooling:1.5.11")
                implementation("org.jetbrains.compose.components:components-resources:1.5.11")



                // Add KMP dependencies here
            }
        }

        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:2.0.21")
            }
        }

        androidMain {
            dependencies {
                implementation("com.google.android.material:material:1.12.0")
                implementation("androidx.datastore:datastore-preferences:1.1.4")
                implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
                implementation("androidx.credentials:credentials:1.5.0")
                implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
                implementation("androidx.compose.ui:ui-text-google-fonts:1.8.2")
                implementation("androidx.activity:activity-compose:1.10.1")
                implementation("com.google.firebase:firebase-auth:23.2.0")

                implementation("com.google.firebase:firebase-firestore:25.1.4")



                implementation("com.google.firebase:firebase-auth:23.2.0")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
                implementation(("com.google.firebase:firebase-bom:33.9.0"))
                implementation(platform("androidx.compose:compose-bom:2025.02.00"))
                implementation("androidx.room:room-common:2.7.1")
                implementation("androidx.room:room-ktx:2.7.1")
                implementation("androidx.room:room-runtime:2.7.1")
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain.
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation("androidx.test:runner:1.5.2")
                implementation("androidx.test:core:1.5.0")
                implementation("androidx.test.ext:junit:1.1.5")
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.
            }
        }
    }

}

