package com.mrbaikal.eardeformaties.ui.main

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mrbaikal.eardeformaties.data.CalculationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.nio.FloatBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _result = MutableStateFlow<Int?>(null)
    val result = _result.asStateFlow()

    fun calculateResult(model: CalculationModel, ortSession: OrtSession, ortEnvironment: OrtEnvironment) {
        try {
            // Get the name of the input node
            val inputName = ortSession.inputNames?.iterator()?.next()
            // Make a FloatBuffer of the inputs
            val floatBufferInputs = FloatBuffer.wrap(
                floatArrayOf(
                    model.age.toFloat(),
                    model.workYear.toFloat(),
                    model.hProtectorState.toFloat(),
                    model.tinnitusState.toFloat(),
                    model.tinnitusState.toFloat()
                )
            )
            // Create input tensor with floatBufferInputs of shape ( 1 , 1 )
            val inputTensor = OnnxTensor.createTensor(ortEnvironment, floatBufferInputs, longArrayOf(1, 1))
            // Run the model
            val results = ortSession.run(mapOf(inputName to inputTensor))
            // Fetch and return the results
            val output = results[0].value as LongArray

            _result.value = output[0].toInt()
        } catch (e: Exception) {
            _result.value = 0
        }
    }
}