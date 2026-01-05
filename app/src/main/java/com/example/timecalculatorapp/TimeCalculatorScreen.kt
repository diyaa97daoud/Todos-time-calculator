package com.example.timecalculatorapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeCalculatorScreen(
    onNavigateToTodos: () -> Unit
) {
    var display by remember { mutableStateOf("0") }
    var currentValue by remember { mutableStateOf<Duration?>(null) }
    var storedDuration by remember { mutableStateOf<Duration?>(null) }
    var currentUnit by remember { mutableStateOf("m") }
    var isNewEntry by remember { mutableStateOf(true) }

    fun addDigit(digit: String) {
        display = if (isNewEntry) {
            isNewEntry = false
            digit
        } else {
            display + digit
        }
    }

    fun setUnit(unit: String) {
        currentUnit = unit
    }

    fun getCurrentDuration(): Duration {
        val value = display.toIntOrNull() ?: 0
        return when (currentUnit) {
            "d" -> Duration(days = value)
            "h" -> Duration(hours = value)
            "m" -> Duration(minutes = value)
            else -> Duration.ZERO
        }
    }

    fun performPlus() {
        val current = getCurrentDuration()
        storedDuration = storedDuration?.let { it + current } ?: current
        display = "0"
        currentUnit = "m"
        isNewEntry = true
    }

    fun performEquals() {
        val current = getCurrentDuration()
        val result = storedDuration?.let { it + current } ?: current
        display = result.toString()
        storedDuration = null
        currentUnit = "m"
        isNewEntry = true
    }

    fun clear() {
        display = "0"
        currentValue = null
        storedDuration = null
        currentUnit = "m"
        isNewEntry = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header with title and navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Time Calculator",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            FilledTonalButton(
                onClick = onNavigateToTodos,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Todos")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .shadow(4.dp, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = display,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Unit selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf(
                "d" to "Days",
                "h" to "Hours",
                "m" to "Minutes"
            ).forEach { (unit, label) ->
                Button(
                    onClick = { setUnit(unit) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentUnit == unit) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (currentUnit == unit)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = if (currentUnit == unit) 6.dp else 2.dp
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = unit,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Number pad
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Rows 1-3
            listOf(
                listOf("7", "8", "9"),
                listOf("4", "5", "6"),
                listOf("1", "2", "3")
            ).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { digit ->
                        FilledTonalButton(
                            onClick = { addDigit(digit) },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = digit,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Bottom row: 0, C
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { clear() },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "C",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                FilledTonalButton(
                    onClick = { addDigit("0") },
                    modifier = Modifier
                        .weight(2f)
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "0",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Operators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { performPlus() },
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "+",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { performEquals() },
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "=",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
