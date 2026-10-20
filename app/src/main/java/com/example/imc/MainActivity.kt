package com.example.imc

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.Switch

import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imc.ui.theme.IMCTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.imc.ui.theme.IMCTheme
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil


//import androidx.compose.runtime.getValue (para o by funcionar)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IMCTheme()   {
                ImcLayout()
            }
        }
    }
}

@Composable
fun ImcLayout() {
    var pesoInput by remember { mutableStateOf(value = "") }
    var alturaInput by remember { mutableStateOf(value = "") }

    val peso = pesoInput.toDoubleOrNull() ?: 0.0
    val altura = alturaInput.toDoubleOrNull() ?: 0.0

    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(all = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = " Calculadora de IMC ",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp).align(alignment = Alignment.Start)
        )

        EditNumberField(
            label = "Peso (KG)",
            value = pesoInput,
            onValueChange = { pesoInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )

        EditNumberField(
            label = "Altura (M)",
            value = alturaInput,
            onValueChange = { alturaInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )

        var imc by remember { mutableStateOf(calcularImc(0.0, 0.0, false)) }

        Button(
            onClick = { imc = calcularImc(peso, altura, false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular") }

        var arredondar by remember { mutableStateOf(false) }
        Button(
            onClick = { imc = calcularImc(peso, altura, true)},
            modifier = Modifier.fillMaxWidth()
        ) { Text("Arredondar") }

        Text(
            text = "IMC: $imc",
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

fun calcularImc(
    peso: Double,
    altura: Double,
    arredondar: Boolean
): String {
    var imc = if (altura > 0.0) { peso / (altura * altura) } else { 0.0 }
    if (arredondar) { imc = ceil(imc) }
    return String.format(Locale("pt", "BR"), "%.2f", imc)
}
fun arredondarImc(imc: Double): Double {
    return ceil(imc)
}



