package com.example.calculatorjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
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
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorJetpackTheme {
                var isDarkTheme by remember { mutableStateOf(false) }
                MinimalistCalculator(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it }
                )
            }
        }
    }
}

// Функция для преобразования градусов в радианы
private fun degreesToRadians(degrees: Double): Double {
    return degrees * (PI / 180.0)
}

@Composable
fun MinimalistCalculator(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf("") }
    var previousNumber by remember { mutableStateOf("") }
    var currentCalculation by remember { mutableStateOf("") }
    var isRadians by remember { mutableStateOf(true) }
    var memoryValue by remember { mutableStateOf("0.0") }
    var showScientificKeypad by remember { mutableStateOf(false) }

    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFFFFFFF)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF333333)
    val operatorButtonColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color(0xFFF5F5F5)
    val numberButtonColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color(0xFFF5F5F5)
    val accentButtonColor = Color(0xFFFF5252)
    val selectedButtonColor = if (isDarkTheme) Color(0xFF444444) else Color(0xFFE0E0E0)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Верхняя панель с заголовком и кнопками
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = textColor,
                    modifier = Modifier.clickable { /* Можно добавить меню */ }
                )
                
                Text(
                    text = "Calc.",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = textColor,
                    modifier = Modifier.clickable { /* Можно добавить действие закрытия */ }
                )
            }
            
            // Область отображения вычислений
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                // Отображение текущего выражения
                if (currentCalculation.isNotEmpty()) {
                    Text(
                        text = currentCalculation,
                        color = textColor.copy(alpha = 0.6f),
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
                
                // Отображение результата
                Text(
                    text = if (result == "0" && input.isNotEmpty()) input else result,
                    color = textColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Клавиатура калькулятора
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (showScientificKeypad) {
                    // Научные функции
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MinimalistCalculatorButton(
                            text = if (isRadians) "RAD" else "DEG",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = { isRadians = !isRadians }
                        )
                        MinimalistCalculatorButton(
                            text = "sin",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                if (input.isNotEmpty()) {
                                    val angle = input.toDoubleOrNull() ?: 0.0
                                    val resultValue = if (isRadians) sin(angle) else sin(degreesToRadians(angle))
                                    input = resultValue.toString()
                                    result = input
                                }
                            }
                        )
                        MinimalistCalculatorButton(
                            text = "cos",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                if (input.isNotEmpty()) {
                                    val angle = input.toDoubleOrNull() ?: 0.0
                                    val resultValue = if (isRadians) cos(angle) else cos(degreesToRadians(angle))
                                    input = resultValue.toString()
                                    result = input
                                }
                            }
                        )
                        MinimalistCalculatorButton(
                            text = "tan",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                if (input.isNotEmpty()) {
                                    val angle = input.toDoubleOrNull() ?: 0.0
                                    val resultValue = if (isRadians) tan(angle) else tan(degreesToRadians(angle))
                                    input = resultValue.toString()
                                    result = input
                                }
                            }
                        )
                    }
                    
                    // Дополнительные функции
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MinimalistCalculatorButton(
                            text = "π",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                input = PI.toString()
                                result = input
                            }
                        )
                        MinimalistCalculatorButton(
                            text = "e",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                input = E.toString()
                                result = input
                            }
                        )
                        MinimalistCalculatorButton(
                            text = "√",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                if (input.isNotEmpty()) {
                                    val value = input.toDoubleOrNull() ?: 0.0
                                    input = sqrt(value).toString()
                                    result = input
                                }
                            }
                        )
                        MinimalistCalculatorButton(
                            text = "^",
                            textColor = textColor,
                            backgroundColor = operatorButtonColor,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (input.isNotEmpty()) {
                                    operation = "^"
                                    previousNumber = input
                                    currentCalculation = "$input ^"
                                    input = ""
                                }
                            }
                        )
                    }
                }
                
                // Основная клавиатура
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MinimalistCalculatorButton(
                        text = "C",
                        textColor = textColor,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input = ""
                            result = "0"
                            operation = ""
                            currentCalculation = ""
                            previousNumber = ""
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "+/-",
                        textColor = textColor,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                input = if (input.startsWith("-")) {
                                    input.substring(1)
                                } else {
                                    "-$input"
                                }
                                result = input
                            } else if (result != "0") {
                                result = if (result.startsWith("-")) {
                                    result.substring(1)
                                } else {
                                    "-$result"
                                }
                            }
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "%",
                        textColor = textColor,
                        backgroundColor = operatorButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                input = (input.toDoubleOrNull()?.div(100) ?: 0.0).toString()
                                result = input
                            }
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "/",
                        textColor = Color.White,
                        backgroundColor = accentButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "/"
                                previousNumber = input
                                currentCalculation = "$input /"
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MinimalistCalculatorButton(
                        text = "7",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "7"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "8",
                        textColor = textColor,
                        backgroundColor = selectedButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "8"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "9",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "9"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "×",
                        textColor = Color.White,
                        backgroundColor = accentButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "*"
                                previousNumber = input
                                currentCalculation = "$input ×"
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MinimalistCalculatorButton(
                        text = "4",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "4"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "5",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "5"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "6",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "6"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "+",
                        textColor = Color.White,
                        backgroundColor = accentButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "+"
                                previousNumber = input
                                currentCalculation = "$input +"
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MinimalistCalculatorButton(
                        text = "1",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "1"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "2",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "2"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "3",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            input += "3"
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "-",
                        textColor = Color.White,
                        backgroundColor = accentButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty()) {
                                operation = "-"
                                previousNumber = input
                                currentCalculation = "$input -"
                                input = ""
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MinimalistCalculatorButton(
                        text = "00",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input != "0") {
                                input += "00"
                                result = input
                            }
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "0",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input != "0") {
                                input += "0"
                                result = input
                            }
                        }
                    )
                    MinimalistCalculatorButton(
                        text = ".",
                        textColor = textColor,
                        backgroundColor = numberButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isEmpty()) {
                                input = "0."
                            } else if (!input.contains(".")) {
                                input += "."
                            }
                            result = input
                        }
                    )
                    MinimalistCalculatorButton(
                        text = "=",
                        textColor = Color.White,
                        backgroundColor = accentButtonColor,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (input.isNotEmpty() && operation.isNotEmpty() && previousNumber.isNotEmpty()) {
                                val num1 = previousNumber.toDoubleOrNull() ?: 0.0
                                val num2 = input.toDoubleOrNull() ?: 0.0
                                val calculationResult = when (operation) {
                                    "+" -> num1 + num2
                                    "-" -> num1 - num2
                                    "*" -> num1 * num2
                                    "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                                    "^" -> num1.pow(num2)
                                    else -> 0.0
                                }

                                val operationSymbol = when (operation) {
                                    "+" -> "+"
                                    "-" -> "-"
                                    "*" -> "×"
                                    "/" -> "/"
                                    "^" -> "^"
                                    else -> ""
                                }

                                currentCalculation = "$previousNumber $operationSymbol $input ="
                                result = calculationResult.toString()
                                input = ""
                                operation = ""
                                previousNumber = result
                            }
                        }
                    )
                }
                
                // Кнопка для переключения между обычной и научной клавиатурой
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { showScientificKeypad = !showScientificKeypad },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = if (showScientificKeypad) "Скрыть научные функции" else "Показать научные функции",
                            color = textColor.copy(alpha = 0.7f)
                        )
                    }
                }
                
                // Переключатель темы
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { onThemeChange(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun MinimalistCalculatorButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}