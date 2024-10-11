import org.jetbrains.kotlin.builtins.StandardNames.FqNames.target
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
}

spotless {
    ratchetFrom("origin/main")
    kotlin {
        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt")
        targetExclude("bin/**/*.kt")
        ktlint("0.49.1")
    }
}