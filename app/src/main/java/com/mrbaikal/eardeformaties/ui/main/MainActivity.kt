package com.mrbaikal.eardeformaties.ui.main

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mrbaikal.eardeformaties.R
import com.mrbaikal.eardeformaties.base.theme.EarDeformatiesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var test: String
    private var isim: String = "Furkan"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test = "Tanım"
        if (::test.isInitialized) {
            Toast.makeText(this, "Tanımlandı", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Tanımlanmadı", Toast.LENGTH_LONG).show()
        }

        setContent {
            EarDeformatiesTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen {
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