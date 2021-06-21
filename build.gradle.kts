group = "com.github.zhengkunwang"
version = "0.0.1"

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

plugins {
    idea
    java
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}
