package com.bibbidi.habittracker.ui.common

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun ComponentActivity.isAlreadyGranted(permission: String) =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun ComponentActivity.isRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun Fragment.isAlreadyGranted(permission: String) =
    ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun Fragment.isRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)
