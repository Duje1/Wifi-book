// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        maven { url =  uri("https://jitpack.io")  }

    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
}

