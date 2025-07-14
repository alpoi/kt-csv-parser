import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

group = "buzz.angus"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "2.2.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
}

repositories {
    mavenCentral()
}

kotlin {
    linuxX64 {
        binaries {
            executable {
                entryPoint = "buzz.angus.main"
                baseName = "csvparse"
            }
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

val linkTask = tasks.named<KotlinNativeLink>("linkReleaseExecutableLinuxX64")

tasks.register<Copy>("packageRelease") {
    dependsOn("linkReleaseExecutableLinuxX64")
    from(linkTask.map { it.outputFile })
    into(layout.projectDirectory.dir("dist"))
    rename { "csvparse" }
}