package marathons.utils.drawing

import java.awt.Color

/**
 * To be seen on white background
 * Can change.
 * Blue is too similar to black.
 */
fun colorDiscrete(x: Int) = when(x) {
	0 -> Color.BLACK
	1 -> Color.RED
	2 -> Color.GREEN
	3 -> Color.BLUE
	4 -> Color.CYAN
	5 -> Color.MAGENTA
	6 -> Color.YELLOW
	7 -> Color.LIGHT_GRAY
	8 -> Color.GRAY
	9 -> Color.DARK_GRAY
	10 -> Color.ORANGE
	11 -> Color.PINK
	else -> Color(x * 43 % 256, x * 77 % 256, x * 101 % 256)
}
