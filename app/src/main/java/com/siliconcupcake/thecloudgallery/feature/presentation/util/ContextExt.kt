package com.siliconcupcake.thecloudgallery.feature.presentation.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

fun Context.restartApplication() {
    val packageManager: PackageManager = packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    val componentName = intent!!.component
    val mainIntent = Intent.makeRestartActivityTask(componentName)
    startActivity(mainIntent)
    Runtime.getRuntime().exit(0)
}