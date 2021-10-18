package marathons.utils.dot;

import java.io.*;

public class DotGraphPrinter {
	PrintWriter out;

	public DotGraphPrinter(PrintWriter writer, String name) {
		out = writer;
		out.println("digraph " + name + " {");
		out.println("node [fontname=Courier,style=filled]");
	}

	public DotGraphPrinter(OutputStream stream, String name) {
		this(new PrintWriter(stream), name);
	}

	public DotGraphPrinter(String filename, String name) {
		this(printWriter(filename), name);
	}

	public static PrintWriter printWriter(String filename) {
		try {
			return new PrintWriter(filename);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		out.println("}");
		out.close();
	}

	public DotGraphPrinter print(DotVertex vertex) {
		out.println(toString(vertex));
		return this;
	}

	public DotGraphPrinter print(DotEdge edge) {
		out.println(toString(edge));
		return this;
	}

	public DotGraphPrinter print(DotGraph graph) {
		for (DotVertex v : graph.vertices()) {
			print(v);
		}
		for (DotEdge e : graph.edges()) {
			print(e);
		}
		return this;
	}

	public void printAndClose(DotGraph graph) {
		print(graph);
		close();
	}

	public static String toString(DotEdge edge) {
		StringBuilder sb = new StringBuilder().append(edge.from()).append(" -> ").append(edge.to());
		StringBuilder prop = new StringBuilder();
		if (!edge.label().isEmpty()) {
			prop.append("label=\"").append(edge.label()).append("\", ");
		}
		if (!edge.style().isEmpty()) {
			prop.append("style=").append(edge.style()).append(", ");
		}
		if (!edge.color().isEmpty()) {
			String color = edge.color();
			prop.append("color=").append('"').append(color).append("\", ");
		}
		if (edge.penWidth() > 0) {
			double penWidth = Math.max(Math.round(10 * edge.penWidth()), 1) / 10.0;
			if (penWidth != 1) {
				prop.append("penwidth=").append(penWidth).append(", ");
			}
		}
		if (edge.weight() > 1) {
			prop.append("weight=").append(edge.weight()).append(", ");
		}
		if (!edge.dir().isEmpty()) {
			prop.append("dir=").append(edge.dir()).append(", ");
		}
		if (!edge.constraint().isEmpty()) {
			prop.append("constraint=").append(edge.constraint()).append(", ");
		}
		if (prop.length() > 0) {
			prop.setLength(prop.length() - 2);
			sb.append(" [").append(prop).append("]");
		}
		return sb.append(";").toString();
	}

	public static String toString(DotVertex vertex) {
		StringBuilder sb = new StringBuilder().append(vertex.id());
		StringBuilder prop = new StringBuilder();
		if (!vertex.label().isEmpty() && !vertex.label().equals(vertex.id())) {
			prop.append("label=\"").append(vertex.label()).append("\", ");
		}
		if (!vertex.shape().isEmpty()) {
			prop.append("shape=").append(vertex.shape()).append(", ");
		}
		if (!vertex.style().isEmpty()) {
			prop.append("style=").append(vertex.style()).append(", ");
		}
		if (!vertex.color().isEmpty()) {
			prop.append("color=").append('"').append(vertex.color()).append("\", ");
		}
		if (!vertex.fillcolor().isEmpty()) {
			prop.append("fillcolor=").append('"').append(vertex.fillcolor()).append("\", ");
		}
		if (vertex.penWidth() > 0) {
			double penWidth = Math.max(Math.round(10 * vertex.penWidth()), 1) / 10.0;
			if (penWidth > 1) {
				prop.append("penwidth=").append(penWidth).append(", ");
			}
		}
		if (vertex.peripheries() > 1) {
			prop.append("peripheries=").append(vertex.peripheries()).append(", ");
		}
		if (prop.length() > 0) {
			prop.setLength(prop.length() - 2);
			sb.append(" [").append(prop).append("]");
		}
		return sb.append(";").toString();
	}
}
