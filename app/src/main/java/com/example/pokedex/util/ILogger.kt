package com.example.pokedex.util

import android.util.Log

/**
 * Logging abstraction used across the app.
 *
 * Enables platform-independent logging and allows for test-safe replacements.
 */
interface ILogger {
    /**
     * Logs an error message with optional exception details.
     *
     * @param tag A tag identifying the source of the log message.
     * @param message The message to log.
     * @param throwable Optional throwable for error context.
     */
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

/**
 * Default Android implementation of [ILogger] that delegates to [Log.e].
 */
object Logger : ILogger {
    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}