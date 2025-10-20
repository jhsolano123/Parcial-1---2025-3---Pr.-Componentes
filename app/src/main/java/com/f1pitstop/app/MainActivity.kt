
// app/src/main/java/com/f1pitstop/app/MainActivity.kt
package com.f1pitstop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.f1pitstop.app.ui.AppNav
import com.f1pitstop.app.ui.theme.F1PitStopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1PitStopTheme {
                AppNav()
            }
        }
    }
}
