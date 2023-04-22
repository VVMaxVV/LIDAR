plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.lidar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(project(":domain"))

                implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
                implementation("io.github.woody230.ktx:compose-constraint-layout:5.0.0")
            }
        }
    }
}
