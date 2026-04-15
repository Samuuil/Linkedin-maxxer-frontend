package com.linkedinmaxxer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.linkedinmaxxer.app.ui.navigation.LMNavHost
import com.linkedinmaxxer.app.ui.theme.LinkedinMaxxerTheme

class MainActivity : ComponentActivity() {

    private val pendingSuggestionId = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pendingSuggestionId.value = intent.getStringExtra(LMFirebaseMessagingService.EXTRA_SUGGESTION_ID)
        enableEdgeToEdge()
        setContent {
            LinkedinMaxxerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        LMNavHost(
                            navController = navController,
                            initialSuggestionId = pendingSuggestionId.value,
                            onSuggestionIdConsumed = { pendingSuggestionId.value = null },
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        pendingSuggestionId.value = intent.getStringExtra(LMFirebaseMessagingService.EXTRA_SUGGESTION_ID)
    }
}
