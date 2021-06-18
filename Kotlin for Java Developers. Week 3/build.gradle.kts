plugins {
    kotlin("jvm") version "1.5.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(kotlin("bom")))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
