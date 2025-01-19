plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.lidar"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
                api("io.insert-koin:koin-core:3.3.2")
                implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.3.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                api("junit:junit:4.13.2")
                api("io.mockk:mockk:1.13.4")
            }
        }
    }
}
