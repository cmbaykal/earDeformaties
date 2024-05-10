package com.mrbaikal.eardeformaties.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrbaikal.eardeformaties.data.CalculationModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCalculate: (CalculationModel) -> Unit = {}
) {
    val context = LocalContext.current

    var ageState by remember { mutableStateOf("30") }
    var workState by remember { mutableStateOf("10") }
    var cigarState by remember { mutableStateOf("10") }

    var hProtectorExpanded by remember { mutableStateOf(false) }
    val hProtectorOptions = listOf("Hayır", "Evet")
    val (hProtectorSelected, onHProtectorSelected) = remember { mutableStateOf(hProtectorOptions[0]) }

    var tinnitusExpanded by remember { mutableStateOf(false) }
    val tinnitusOptions = listOf("Yok", "Tek kulakta var", "İki kulakta var")
    val (tinnitusSelected, ontinnitusSelected) = remember { mutableStateOf(tinnitusOptions[0]) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = ageState,
                onValueChange = { ageState = it },
                placeholder = {
                    Text(
                        "Yaşınız"
                    )
                },
                keyboardActions = KeyboardActions.Default
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true,
                value = workState,
                onValueChange = { workState = it },
                placeholder = {
                    Text("Çalışma Yılınız")
                }
            )

            ExposedDropdownMenuBox(
                modifier = Modifier.padding(top = 16.dp),
                expanded = hProtectorExpanded,
                onExpandedChange = {
                    hProtectorExpanded = !hProtectorExpanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = hProtectorSelected,
                    onValueChange = { },
                    label = { Text("İşitme Koruyucu Durumu") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = hProtectorExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = hProtectorExpanded,
                    onDismissRequest = {
                        hProtectorExpanded = false
                    }
                ) {
                    hProtectorOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                onHProtectorSelected(option)
                                hProtectorExpanded = false
                            },
                            text = {
                                Text(text = option)
                            }
                        )
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                value = cigarState,
                onValueChange = { cigarState = it },
                placeholder = {
                    Text("Sigara İçme Süresi (Yıl)")
                }
            )


            ExposedDropdownMenuBox(
                modifier = Modifier.padding(top = 16.dp),
                expanded = tinnitusExpanded,
                onExpandedChange = {
                    tinnitusExpanded = !tinnitusExpanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = tinnitusSelected,
                    onValueChange = { },
                    label = { Text("Kulak Çınlaması Durumu") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = tinnitusExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = tinnitusExpanded,
                    onDismissRequest = {
                        tinnitusExpanded = false
                    }
                ) {
                    tinnitusOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                ontinnitusSelected(option)
                                tinnitusExpanded = false
                            },
                            text = {
                                Text(text = option)
                            }
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = {
                    if (ageState.isNotEmpty() && workState.isNotEmpty() && cigarState.isNotEmpty()) {
                        val model = CalculationModel(
                            age = ageState.toInt(),
                            workYear = workState.toInt(),
                            hProtectorState = hProtectorOptions.indexOf(hProtectorSelected),
                            cigarYear = cigarState.toInt(),
                            tinnitusState = tinnitusOptions.indexOf(tinnitusSelected)
                        )
                        onCalculate.invoke(model)
                    } else {
                        Toast.makeText(context, "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(
                    fontSize = 16.sp,
                    text = "Hesapla"
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}