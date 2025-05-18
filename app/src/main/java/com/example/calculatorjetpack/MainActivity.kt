package com.example.calculatorjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculatorjetpack.ui.theme.CalculatorJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorJetpackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Reult: $result", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter expression") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { input += "1" }) { Text("1") }
            Button(onClick = { input += "2" }) { Text("2") }
            Button(onClick = { input += "3" }) { Text("3") }
            Button(onClick = { input += " + " }) { Text("+") }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { input += "4" }) { Text("4") }
            Button(onClick = { input += "5" }) { Text("5") }
            Button(onClick = { input += "6" }) { Text("6") }
            Button(onClick = { input += " - " }) { Text("-") }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { input += "7" }) { Text("7") }
            Button(onClick = { input += "8" }) { Text("8") }
            Button(onClick = { input += "9" }) { Text("9") }
            Button(onClick = { input += " * " }) { Text("*") }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { input += "0" }) { Text("0") }
            Button(onClick = { result = calculate(input); input = "" }) { Text("=") }
            Button(onClick = { input += " / " }) { Text("/") }
        }
    }
}

fun calculate(expression: String): String {
    return try {
        val parts = expression.split(" ")
        val num1 = parts[0].toDouble()
        val operator = parts[1]
        val num2 = parts[2].toDouble()
        when (operator) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> (num1 / num2).toString()
            else -> ""
        }
    } catch (e: Exception) {
        "Error"
    }
}