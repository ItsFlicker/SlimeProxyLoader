plugins {
    `java-library`
    id("io.izzel.taboolib") version "1.34"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    install("common")
    install("module-configuration")
    install("module-database")
    install("platform-bukkit")
    classifier = null
    version = "6.0.7-39"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11802:11802:mapped")
    compileOnly("ink.ptms.core:v11802:11802:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}