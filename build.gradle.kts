
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.0")
        classpath(kotlin("gradle-plugin", version = "1.9.23"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
