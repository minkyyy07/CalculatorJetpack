package com.example.calculatorjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorjetpack.ui.theme.CalculatorJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorJetpackTheme {
                var isDarkTheme by remember { mutableStateOf(false) }

                val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFADE1F5)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ModernCalculator(
                            isDarkTheme = isDarkTheme,
                            onThemeChange = { isDarkTheme = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModernCalculator(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf("") }
    var history by remember { mutableStateOf("") }
    var historyList by remember { mutableStateOf(mutableListOf<String>()) }

    val backgroundColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF333333)
    val operatorButtonColor = if (isDarkTheme) Color(0xFF0D6EFD) else Color(0xFF0D6EFD)
    val numberButtonColor = if (isDarkTheme) Color(0xFF2A2A2A) else Color(0xFFF5F5F5)
    val accentButtonColor = if (isDarkTheme) Color(0xFF0D6EFD) else Color(0xFF0D6EFD)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color(0xFF121212) else Color(0xFFADE1F5))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeChange(it) },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.End
            ) {
                if (history.isNotEmpty()) {
                    Text(
                        text = history,
                        color = textColor.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Text(
                    text = if (result == "0" && input.isNotEmpty()) input else result,
                    color = textColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = 8.dp),
                reverseLayout = true
            ) {
                items(historyList.size) { index ->
                    Text(
                        text = historyList[index],
                        modifier = Modifier.padding(8.dp),
                        color = textColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModernCalculatorButton(
                        text = "AC",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input = ""
                            result = "0"
                            operation = ""
                            history = ""
                            historyList.clear()
                        }
                    )
                    ModernCalculatorButton(
                        text = "+/-",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                input = if (input.startsWith("-")) {
                                    input.substring(1)
                                } else {
                                    "-$input"
                                }
                                if (operation.isEmpty()) result = input
                            }
                        }
                    )
                    ModernCalculatorButton(
                        text = "%",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                val value = input.toDoubleOrNull() ?: 0.0
                                result = (value / 100).toString()
                                input = result
                            }
                        }
                    )
                    ModernCalculatorButton(
                        text = "÷",
                        textColor = Color.White,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "÷"
                                history = input
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModernCalculatorButton(
                        text = "7",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "7"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "8",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "8"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "9",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "9"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "×",
                        textColor = Color.White,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "×"
                                history = input
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModernCalculatorButton(
                        text = "4",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "4"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "5",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "5"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "6",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "6"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "-",
                        textColor = Color.White,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "-"
                                history = input
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModernCalculatorButton(
                        text = "1",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "1"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "2",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "2"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "3",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "3"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = "+",
                        textColor = Color.White,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "+"
                                history = input
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ModernCalculatorButton(
                        text = "0",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(2f),
                        onClick = {
                            input += "0"
                            if (operation.isEmpty()) result = input
                        }
                    )
                    ModernCalculatorButton(
                        text = ".",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (!input.contains(".")) {
                                input += if (input.isEmpty()) "0." else "."
                                if (operation.isEmpty()) result = input
                            }
                        }
                    )
                    ModernCalculatorButton(
                        text = "=",
                        textColor = Color.White,
                        backgroundColor = accentButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty() && history.isNotEmpty()) {
                                val num1 = history.toDoubleOrNull() ?: 0.0
                                val num2 = input.toDoubleOrNull() ?: 0.0
                                val calculatedResult = when (operation) {
                                    "+" -> num1 + num2
                                    "-" -> num1 - num2
                                    "×" -> num1 * num2
                                    "÷" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                                    else -> num2
                                }

                                val displayHistory = "$history $operation $input = $result"
                                historyList.add(0, displayHistory)
                                if (historyList.size > 10) {
                                    historyList.removeAt(historyList.lastIndex)
                                }
                                result = if (calculatedResult.isNaN()) "Error" else calculatedResult.toString()
                                input = ""
                                operation = ""
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ModernCalculatorButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(60.dp)
            .aspectRatio(1f, matchHeightConstraintsFirst = true),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )
    }
}