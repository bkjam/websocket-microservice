plugins {
    base
    kotlin("jvm") version "1.5.31" apply false
    kotlin("plugin.spring") version "1.5.31" apply false
}

allprojects {
    group = "com.example"
    repositories {
        mavenCentral()
    }
}
