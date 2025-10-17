plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(files("libs/fastcgi-lib.jar"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar{
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest{
        attributes["Main-Class"] = "app.Main"
    }
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
    archiveFileName.set("app.jar")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}