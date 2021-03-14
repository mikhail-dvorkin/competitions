package marathons.utils;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Pictures {
	static boolean mode = false;
	static File picsFile = new File("pics~.html");
	private static PrintWriter html;

	public static void init() {
		try {
			if (!mode) {
				mode = true;
				html = new PrintWriter(picsFile);
				html.println("<html><body>");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void write(BufferedImage image) {
		write(image, Evaluator._seed, Evaluator.settings().getProperty("width", ""));
	}

	public static void write(String fileName) {
		write(fileName, Evaluator._seed, Evaluator.settings().getProperty("width", ""));
	}

	public static void write(BufferedImage image, long seed, String width) {
		init();
		String format = "png";
		String fileName = seed + "~." + format;
		try {
			ImageIO.write(image, format, new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException();
		}
		write(fileName, seed, width);
	}

	public static void write(String fileName, long seed, String width) {
		init();
		String[] fileNameSplit = fileName.split("\\.");
		String format = fileNameSplit[fileNameSplit.length - 1];
		html.println("<nobr>" + String.format("%03d", seed));
		html.println("<img src=\"" + fileName + "\"");
		if (!width.isEmpty()) {
			html.println("width=\"" + width + "\"");
		}
		html.println("/></nobr>");
		html.flush();
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
