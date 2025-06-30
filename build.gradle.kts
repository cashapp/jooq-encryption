plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.33.0"
    kotlin("jvm") version "1.9.22"
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
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

kotlin {
    jvmToolchain(17)
}

sourceSets {
    test {
        java {
            srcDir("src/test/jooq-generated")
        }
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
