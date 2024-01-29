package com.venom.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.venom.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {

    var inputValue by remember {
        mutableStateOf("")
    }
    var outputValue by remember {
        mutableStateOf("0.0")
    }
    var inputUnit by remember {
        mutableStateOf("Meters")
    }
    var outputUnit by remember {
        mutableStateOf("Meters")
    }
    var iExpanded by remember {
        mutableStateOf(false)
    }
    var oExpanded by remember {
        mutableStateOf(false)
    }
    val iConversionFactor = remember { mutableDoubleStateOf(1.00) }
    val oConversionFactor = remember { mutableDoubleStateOf(1.00) }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * iConversionFactor.doubleValue * 100.0 / oConversionFactor.doubleValue).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter",
                style = MaterialTheme.typography.headlineLarge
            )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            convertUnits()
        })
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
                Button(onClick = {
                    iExpanded = true
                }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Centimeters"
                        iConversionFactor.doubleValue = 0.01
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Meters"
                        iConversionFactor.doubleValue = 1.0
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        iExpanded = false
                        inputUnit = "Feet"
                        iConversionFactor.doubleValue = 0.3048
                    })
                    DropdownMenuItem(text = { Text("Millimeters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Millimeters"
                            iConversionFactor.doubleValue = 0.001
                        })
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box {
                Button(onClick = {
                    oExpanded = true
                }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Centimeters"
                            oConversionFactor.doubleValue = 0.01
                        })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        oExpanded = false
                        outputUnit = "Meters"
                        oConversionFactor.doubleValue = 1.00
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        oExpanded = false
                        outputUnit = "Feet"
                        oConversionFactor.doubleValue = 0.3048
                    })
                    DropdownMenuItem(text = { Text("Millimeters") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "Millimeters"
                            oConversionFactor.doubleValue = 0.001
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Result: $outputValue $outputUnit",
            style=MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {

    UnitConverter()
}