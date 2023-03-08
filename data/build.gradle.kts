plugins {
    kotlin("multiplatform")
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
            }
        }
    }
}
