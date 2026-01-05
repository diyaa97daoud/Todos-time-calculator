package com.example.timecalculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timecalculatorapp.ui.theme.TimeCalculatorAppTheme

enum class Screen {
    CALCULATOR, TODO_LIST, CREATE_TODO
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeCalculatorAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimeCalculatorApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TimeCalculatorApp(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(Screen.CALCULATOR) }

    when (currentScreen) {
        Screen.CALCULATOR -> {
            TimeCalculatorScreen(
                onNavigateToTodos = { currentScreen = Screen.TODO_LIST }
            )
        }
        Screen.TODO_LIST -> {
            TodoListScreen(
                todos = viewModel.todos,
                onToggleSelection = { todoId -> viewModel.toggleSelection(todoId) },
                onFinish = { viewModel.finishSelectedTodos() },
                onNavigateToCreate = { currentScreen = Screen.CREATE_TODO },
                onNavigateBack = { currentScreen = Screen.CALCULATOR }
            )
        }
        Screen.CREATE_TODO -> {
            CreateTodoScreen(
                onNavigateBack = { currentScreen = Screen.TODO_LIST },
                onTodoCreated = { description, duration ->
                    viewModel.addTodo(description, duration)
                }
            )
        }
    }
}