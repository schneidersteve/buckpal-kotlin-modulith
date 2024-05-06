import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.google.devtools.ksp)
    id("groovy")
    alias(libs.plugins.micronaut.application)
}

micronaut {
    runtime("netty")
    testRuntime("spock2")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.micronaut.flyway)
    implementation(libs.micronaut.jdbc.hikari)

    implementation(project(":buckpal", "buckpalJars"))

    runtimeOnly(libs.logback.classic)
    runtimeOnly(libs.h2)
    runtimeOnly(libs.r2dbc.h2)
    runtimeOnly(libs.r2dbc.pool)
    runtimeOnly(libs.snakeyaml)
    runtimeOnly(libs.micronaut.reactor)
    runtimeOnly(libs.micronaut.kotlin.runtime)
    runtimeOnly(libs.kotlin.reflect)
    runtimeOnly(libs.kotlinx.coroutines.reactor)

    testImplementation(project(":buckpal", "buckpalJars"))

    testImplementation(libs.micronaut.http.client)
    testImplementation(libs.kotlinx.coroutines.core)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
    kotlinOptions.javaParameters = true
}

application {
    mainClass.set("buckpal.kotlin.main.MainKt")
}
