plugins {
    id("java")
    kotlin("jvm") version "1.7.20"
}

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
    }
}

apply(plugin = "com.vanniktech.maven.publish")
apply(from = "$rootDir/gradle-mvn-publish.gradle")

group = "app.cash.jooq"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.crypto.tink:tink-android:1.7.0")
    implementation("org.jooq:jooq-codegen:3.17.6")
    implementation("org.jooq:jooq-meta:3.17.6")
    implementation("org.jooq:jooq-meta-extensions:3.17.6")

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