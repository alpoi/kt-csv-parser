group = "buzz.angus"
version = "0.3.3"

plugins {
    kotlin("multiplatform") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
}

repositories {
    mavenCentral()
}

kotlin {
    val targets = listOf(linuxX64(), macosX64(), macosArm64())
    targets.forEach {
        target -> target.binaries.executable {
            entryPoint = "buzz.angus.main"
            baseName = "csv-parse"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
                implementation("com.github.ajalt.clikt:clikt:5.0.3")
            }
        }
    }
}
