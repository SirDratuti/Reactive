plugins {
    kotlin("jvm") version "1.8.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.reactivex:rxjava:1.3.8")
    implementation("io.reactivex:rxnetty-http:0.5.3")
    implementation("io.reactivex:rxnetty-common:0.5.3")
    implementation("io.reactivex:rxnetty-tcp:0.5.3")
    implementation("org.mongodb:mongodb-driver-rx:1.5.0")
    implementation("io.netty:netty-all:4.1.90.Final")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}