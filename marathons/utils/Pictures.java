package marathons.utils;

import marathons.utils.dot.DotGraph;
import marathons.utils.dot.DotGraphPrinter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

public class Pictures {
	static boolean mode = false;
	static File picsFile = new File("pics~.html");
	static String format = "png";
	private static PrintWriter html;
	public static int width = 1000, height = 800, infoFontWidth = 12, infoFontHeight = 12;
	static Map<Long, Integer> seedFrame = new HashMap<>();

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

	public static File picsDir() {
		File dir = new File("img_" + Evaluator._project + "~");
		dir.mkdirs();
		return dir;
	}

	public static String picName(String fileExtension, int frame) {
		String frameLabel = frame == -1 ? "" : "_" + String.format("%05d", frame);
		return "pic" + String.format("%03d", Evaluator._seed) + frameLabel + "." + fileExtension;
	}

	public static File newFrameFile(String fileExtension) {
		int frame = seedFrame.getOrDefault(Evaluator._seed, 0);
		seedFrame.put(Evaluator._seed, frame + 1);
		return new File(picsDir(), picName(fileExtension, frame));
	}

	public static void write(Consumer<Graphics> painter) {
		BufferedImage image = new BufferedImage(Pictures.width, Pictures.height, BufferedImage.TYPE_INT_RGB);
		painter.accept(image.getGraphics());
		write(image);
	}

	public static void write(DotGraph graph, String advisedDotProgram) {
		try {
			File dotFile = newFrameFile("dot");
			PrintWriter dotFileWriter = new PrintWriter(dotFile);
			new DotGraphPrinter(dotFileWriter, dotFile.getName().replaceAll("\\W", "")).printAndClose(graph);
			PrintWriter sh = DotGraphPrinter.printWriter("pics~.sh");
			sh.println("cd " + picsDir());
			sh.println(advisedDotProgram + " -O -T" + format + " *.dot");
			sh.close();
			writeHtmlAbout(new File(dotFile.getParent(), dotFile.getName() + "." + format));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
//		Pictures.write("pic" + Evaluator._seed + "~.dot." + format);
//		Pictures.br();
	}

	public static void write(BufferedImage image) {
		init();
		File file = newFrameFile(format);
		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		writeHtmlAbout(file);
	}

	public static void writeByRenaming(File existingFile) {
		File newFile = Pictures.newFrameFile(ResourcesKt.extensionOfFile(existingFile));
		boolean result = existingFile.renameTo(newFile);
		if (!result) {
			throw new RuntimeException(new IOException("No " + existingFile));
		}
		writeHtmlAbout(newFile);
	}

	public static void writeHtmlAbout(File file) {
		init();
		String width = Evaluator.settings().getProperty("width", "");
		html.println("<nobr>" + String.format("%03d", Evaluator._seed));
		html.println("<img src=\"" + file.getPath() + "\"");
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

	public static void remind() {
		if (mode) {
			System.out.println("file://" + picsFile.getAbsolutePath());
		}
	}
}
