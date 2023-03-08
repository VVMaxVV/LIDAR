plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.lidar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
                api("io.insert-koin:koin-core:3.3.2")
            }
        }
    }
}
