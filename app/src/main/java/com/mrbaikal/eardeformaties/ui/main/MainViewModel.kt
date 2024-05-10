package com.mrbaikal.eardeformaties.ui.main

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import androidx.lifecycle.ViewModel
import com.mrbaikal.eardeformaties.data.CalculationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.nio.FloatBuffer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    fun calculateResult(model: CalculationModel, ortSession: OrtSession, ortEnvironment: OrtEnvironment) {
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
        println("Test output : ${output[0]}")
    }
}