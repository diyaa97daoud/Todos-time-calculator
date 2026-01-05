package com.example.timecalculatorapp

data class Duration(
    val days: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0
) {
    init {
        require(days >= 0) { "Days must be non-negative" }
        require(hours >= 0) { "Hours must be non-negative" }
        require(minutes >= 0) { "Minutes must be non-negative" }
    }

    operator fun plus(other: Duration): Duration {
        val totalMinutes = this.minutes + other.minutes
        val totalHours = this.hours + other.hours + totalMinutes / 60
        val totalDays = this.days + other.days + totalHours / 24

        return Duration(
            days = totalDays,
            hours = totalHours % 24,
            minutes = totalMinutes % 60
        )
    }

    override fun toString(): String {
        val parts = mutableListOf<String>()
        if (days > 0) parts.add("${days}d")
        if (hours > 0) parts.add("${hours}h")
        if (minutes > 0) parts.add("${minutes}m")
        return if (parts.isEmpty()) "0m" else parts.joinToString(" ")
    }

    companion object {
        val ZERO = Duration(0, 0, 0)
    }
}
