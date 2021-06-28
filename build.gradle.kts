group = "com.github.zhengkunwang"
version = "0.0.1"

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(kotlin("reflect"))
    implementation("org.apache.poi:poi-ooxml:5.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.19.0")
}

plugins {
    idea
    java
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}
