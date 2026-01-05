package com.example.timecalculatorapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class TodoViewModel : ViewModel() {
    private val _todos = mutableStateListOf<Todo>()
    val todos: List<Todo> get() = _todos

    fun addTodo(description: String, duration: Duration) {
        val todo = Todo(
            id = UUID.randomUUID().toString(),
            timedDescription = Timed(duration, description)
        )
        _todos.add(todo)
    }

    fun toggleSelection(todoId: String) {
        val index = _todos.indexOfFirst { it.id == todoId }
        if (index != -1) {
            _todos[index] = _todos[index].copy(isSelected = !_todos[index].isSelected)
        }
    }

    fun finishSelectedTodos(): Pair<Duration, List<String>>? {
        val selectedTodos = _todos.filter { it.isSelected }
        if (selectedTodos.isEmpty()) return null

        val timedList = selectedTodos.map { it.timedDescription }
        val combined = Timed.combine(timedList)

        // Remove finished todos
        _todos.removeAll { it.isSelected }

        return Pair(combined.duration, combined.value)
    }
}
