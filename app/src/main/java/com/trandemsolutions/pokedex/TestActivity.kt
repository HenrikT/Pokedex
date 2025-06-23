package com.trandemsolutions.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Host activity for Compose UI tests. Empty by design.
 *
 * Used in tests to allow calling `setContent {}` without conflict.
 */
@AndroidEntryPoint
class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Intentionally does NOT call setContent {}
    }
}