package com.example.compose_kotlin.utilities

import android.app.Activity
import android.widget.Toast

fun Activity.error(text: String) {
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_SHORT
    ).show()
}