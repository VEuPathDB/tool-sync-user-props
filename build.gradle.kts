import org.gradle.kotlin.dsl.dependencies

plugins {
    java
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21"
    id("com.gradleup.shadow") version "9.1.0"
}

group = "org.veupathdb"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
  compilerOptions {
    freeCompilerArgs = listOf("-Xjvm-default=all")
  }
}

tasks.shadowJar {
  exclude("**/Log4j2Plugins.dat")
  archiveFileName.set("service.jar")
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    manifest {
        // Optionally, set the main class for the shadowed JAR.
        attributes["Main-Class"] = "org.veupathdb.syncuserprops.MainKt"
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "GitHubPackages"
        url  = uri("https://maven.pkg.github.com/veupathdb/maven-packages")
        credentials {
            username = if (extra.has("gpr.user")) extra["gpr.user"] as String? else System.getenv("GITHUB_USERNAME")
            password = if (extra.has("gpr.key")) extra["gpr.key"] as String? else System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {

    implementation("com.charleskorn.kaml:kaml-jvm:0.104.0")

    implementation("org.gusdb:fgputil-core:2.18.0")
    implementation("org.gusdb:fgputil-db:2.18.0")

    //testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //testImplementation("org.junit.jupiter:junit-jupiter:5.14.1")
    //testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.1")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
