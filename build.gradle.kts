plugins {
    `java-library`
    id("io.izzel.taboolib") version "1.34"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    description {
        dependencies {
            name("Slimefun")
        }
    }
    install("common")
    install("common-5")
    install("module-configuration")
    install("module-database")
    install("platform-bukkit")
    classifier = null
    version = "6.0.7-44"
}

repositories {
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v11802:11802:mapped")
    compileOnly("ink.ptms.core:v11802:11802:universal")
    compileOnly("ink.ptms:nms-all:1.0.0")

    compileOnly("com.github.Slimefun:Slimefun4:RC-31")

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