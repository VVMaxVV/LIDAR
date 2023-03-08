pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("org.jetbrains.kotlin.jvm") version "1.8.0"
        id("org.jlleitschuh.gradle.ktlint") version "11.3.1" apply false
    }
}

rootProject.name = "Lidar"

include("core")
include("data")
include("domain")
include("presenter")
