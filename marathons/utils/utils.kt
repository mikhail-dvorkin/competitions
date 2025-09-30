package marathons.utils

fun Int.ordinal() = "$this" + when {
	(this % 100 in 11..13) -> "th"
	(this % 10) == 1 -> "st"
	(this % 10) == 2 -> "nd"
	(this % 10) == 3 -> "rd"
	else -> "th"
}
