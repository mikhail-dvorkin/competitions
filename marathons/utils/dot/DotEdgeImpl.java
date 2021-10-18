package marathons.utils.dot;

public class DotEdgeImpl implements DotEdge {
	private String from;
	private String to;
	private String label = "";
	private String style = "";
	private String color = "";
	private double penWidth = 0;
	private int weight = 0;
	private String dir = "";
	private String constraint = "";

	public DotEdgeImpl(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public DotEdgeImpl(DotVertex from, DotVertex to) {
		this.from = from.id();
		this.to = to.id();
	}

	@Override
	public String from() {
		return from;
	}

	@Override
	public String to() {
		return to;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public String style() {
		return style;
	}

	@Override
	public String color() {
		return color;
	}

	@Override
	public double penWidth() {
		return penWidth;
	}

	@Override
	public int weight() {
		return weight;
	}

	@Override
	public String dir() {
		return dir;
	}

	@Override
	public String constraint() {
		return constraint;
	}

	public DotEdgeImpl setLabel(String label) {
		this.label = label;
		return this;
	}

	public DotEdgeImpl setStyle(String style) {
		this.style = style;
		return this;
	}

	public DotEdgeImpl setColor(String color) {
		this.color = color;
		return this;
	}

	public DotEdgeImpl setPenWidth(double penWidth) {
		this.penWidth = penWidth;
		return this;
	}

	public DotEdgeImpl setWeight(int weight) {
		this.weight = weight;
		return this;
	}

	public DotEdgeImpl undirected() {
		this.dir = "none";
		return this;
	}

	public DotEdgeImpl constraintless() {
		this.constraint = "false";
		return this;
	}

	@Override
	public String toString() {
		return DotGraphPrinter.toString(this);
	}
}
