plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.fodded"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.2")
    implementation("io.netty:netty-all:4.1.112.Final")
    implementation("org.jetbrains:annotations:26.0.2")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("JavaRedis")
    archiveVersion.set("1.0-SNAPSHOT")
    manifest {
        attributes["Main-Class"] = "me.fodded.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}