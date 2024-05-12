// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    // firebase
    id("com.google.gms.google-services") version "4.4.1" apply false
    // hilt
    id("com.google.dagger.hilt.android") version "2.50" apply false
}

buildscript {
    dependencies {
        // hiding api keys plugin
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}