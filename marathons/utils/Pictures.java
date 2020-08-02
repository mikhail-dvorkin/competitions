package marathons.utils;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Pictures {
	static boolean mode = false;
	private static PrintWriter html;

	public static void init() {
		try {
			if (!mode) {
				mode = true;
				html = new PrintWriter("pics~.html");
				html.println("<html><body>");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void write(BufferedImage image) {
		try {
			init();
			String format = "png";
			String fileName = Evaluator._seed + "~." + format;
			ImageIO.write(image, format, new File(fileName));
			html.println("<nobr>" + Evaluator._seed);
			html.println("<img src=\"" + fileName + "\"");
			String width = Evaluator.settings().getProperty("width", "");
			if (!width.isEmpty()) {
				html.println("width=\"" + width + "\"");
			}
			html.println("/></nobr>");
			html.flush();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void writeHtml(CharSequence message) {
		init();
		html.println(message);
		html.flush();
	}

	public static void write(CharSequence s) {
		writeHtml("<pre>" + s + "</pre>");
	}

	public static void br() {
		init();
		html.println("<br/>");
		html.flush();
	}
}