plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:" + System.getProperty("spring_version")))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:" + System.getProperty("kotlin_coroutines_version"))
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:" + System.getProperty("kotlin_coroutines_version"))
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:" + System.getProperty("kotlin_coroutines_version"))
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:" + System.getProperty("kotlin_coroutines_version"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-avro")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-ion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-protobuf")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-smile")

    implementation("ch.qos.logback:logback-classic")
    implementation("io.github.oshai:kotlin-logging-jvm:" + System.getProperty("kotlin_logging_version"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:" + System.getProperty("kotlin_coroutines_version"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-opt-in=kotlin.ExperimentalStdlibApi",
            "-opt-in=kotlin.io.encoding.ExperimentalEncodingApi"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
