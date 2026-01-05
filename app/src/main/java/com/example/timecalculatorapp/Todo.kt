package com.example.timecalculatorapp

data class Todo(
    val id: String,
    val timedDescription: Timed<String>,
    val isSelected: Boolean = false
)
