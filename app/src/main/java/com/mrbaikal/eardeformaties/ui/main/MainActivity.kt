package com.mrbaikal.eardeformaties.ui.main

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.mrbaikal.eardeformaties.R
import com.mrbaikal.eardeformaties.base.theme.EarDeformatiesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EarDeformatiesTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val riskState = viewModel.result.collectAsState()

                    MainScreen(
                        riskState.value
                    ) {
                        val environment = OrtEnvironment.getEnvironment()
                        val session = createORTSession(environment)
                        viewModel.calculateResult(it, session, environment)
                    }
                }
            }
        }
    }

    private fun createORTSession(environment: OrtEnvironment): OrtSession {
        val modelBytes = resources.openRawResource(R.raw.model).readBytes()
        return environment.createSession(modelBytes)
    }
}