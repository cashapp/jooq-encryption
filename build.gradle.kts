plugins {
    id("java")
    kotlin("jvm") version "1.7.20"
}

group = "app.cash.jooq.encryption"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.crypto.tink:tink-android:1.7.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.jooq:jooq-codegen:3.17.6")
    implementation("org.jooq:jooq-meta:3.17.6")
    implementation("org.jooq:jooq-meta-extensions:3.17.6")

    testImplementation("org.assertj:assertj-core:3.24.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

sourceSets.test {
    java.srcDirs("src/test/jooq-generated")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}