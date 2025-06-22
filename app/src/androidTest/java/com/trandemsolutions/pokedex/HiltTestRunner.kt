package com.trandemsolutions.pokedex

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom test runner required to enable Hilt in instrumented tests.
 *
 * Replaces the default [Application] with [HiltTestApplication] so that
 * dependency injection works in `@HiltAndroidTest` classes.
 *
 * Referenced in `build.gradle.kts`:
 *
 * ```
 * defaultConfig {
 *     testInstrumentationRunner = "com.trandemsolutions.pokedex.HiltTestRunner"
 * }
 * ```
 *
 * Without this runner, Hilt cannot initialize its component hierarchy during UI tests.
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}