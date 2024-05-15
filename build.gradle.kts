buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.conviva.sdk:android-plugin:0.3.3")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.hilt) apply false
}
