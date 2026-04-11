package com.ossalali.sessionzero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ossalali.sessionzero.navigation.AppNavigation
import com.ossalali.sessionzero.ui.theme.SessionZeroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SessionZeroTheme {
                AppNavigation()
            }
        }
    }
}
