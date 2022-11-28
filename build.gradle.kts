import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "id.walt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.walt.id/repository/waltid/")
    maven("https://maven.walt.id/repository/waltid-ssi-kit/")
    maven("https://repo.danubetech.com/repository/maven-public/")
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {

    // walt.id
    implementation("id.walt:waltid-ssi-kit:1.13.0-SNAPSHOT")
    implementation("id.walt.servicematrix:WaltID-ServiceMatrix:1.1.2")
    implementation("id.walt:waltid-ssikit-vclib:1.23.5")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.0-alpha5")

    // Kotlin
    implementation(kotlin("stdlib"))

    // JSON
    implementation("com.beust:klaxon:5.6")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}
