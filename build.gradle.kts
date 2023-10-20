// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

buildscript {
    dependencies{
        classpath("com.github.megatronking.stringfog:gradle-plugin:5.1.0")
        classpath("com.github.megatronking.stringfog:xor:5.0.0")
    }
}