plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.25.3"
    kotlin("jvm") version "1.7.20"
}

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.crypto.tink:tink-android:1.7.0")
    implementation("org.jooq:jooq-codegen:3.19.1")
    implementation("org.jooq:jooq-meta:3.19.1")
    implementation("org.jooq:jooq-meta-extensions:3.19.1")

    testImplementation("org.assertj:assertj-core:3.24.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

kotlin {
    jvmToolchain(18)
}

sourceSets.test {
    java.srcDirs("src/test/jooq-generated")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}