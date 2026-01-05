# Time Calculator & Todo Manager

An Android application for calculating time durations and managing timed tasks.

## Features

### ⏱️ Time Calculator

- Calculate time using days, hours, and minutes
- Add multiple durations together
- Automatic normalization (60 min → 1 hour, 24 hours → 1 day)

### ✅ Todo Management

- Create todos with time estimates
- Track multiple tasks with their durations
- Select and finish todos to see total accumulated time

## Built With

- **Kotlin** - Pure Kotlin implementation
- **Jetpack Compose** - Modern Android UI
- **Material Design 3** - Beautiful, responsive design

## Key Concepts

- Custom `Duration` data class with operator overloading
- `Timed<T>` monad with map, flatMap, and combine functions
- Immutable data structures
- Functional programming patterns

## How to Use

**Calculator:**

1. Enter a number using digit buttons
2. Select unit (d/h/m)
3. Press `+` to add
4. Repeat for more durations
5. Press `=` to see result

**Todos:**

1. Navigate to Todos
2. Create new tasks with descriptions and time estimates
3. Select tasks by tapping them
4. Press Finish to complete selected tasks and see total time
