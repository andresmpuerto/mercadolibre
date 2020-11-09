package com.andrempuerto.meli.utils

import android.util.Log
import com.andrempuerto.meli.BuildConfig.DEBUG
//import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object Logger {

    private var tag: String = ""

    fun init() {
        if (DEBUG) Timber.plant(Timber.DebugTree()) else Timber.plant(ReleaseTree())
    }

    fun setTag(tag: String){
        this.tag = tag
    }

    fun d(s: String, objects: Array<Any>? = arrayOf()) {
        Timber.tag("DEBUG $tag => ").d(s, objects)
    }

    fun e(s: String? = null, objects: Array<Any>? = arrayOf(), exception: Exception? = null) {
        exception?.let {
            Timber.tag("EXCEPTION $tag => ").e(it)
        } ?: run {
            Timber.tag("ERROR $tag => ").e(s, objects)
        }
    }

    fun w(s: String, objects: Array<Any>? = arrayOf()) {
        Timber.tag("WARNING $tag => ").w(s, objects)
    }

    fun i(s: String, objects: Array<Any>? = arrayOf()) {
        Timber
            .tag("INFO $tag => ").i(s, objects)
    }
}

class ReleaseTree : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return when (priority) {
            Log.DEBUG, Log.INFO, Log.VERBOSE -> false
            else -> true
        }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            //TODO Reportar to Firebase Crashlytics
//            FirebaseCrashlytics.getInstance().also {
//                t?.let { th ->
//                    it.recordException(th)
//                } ?: run {
//                    it.log(message)
//                }
//            }
        }
    }
}
