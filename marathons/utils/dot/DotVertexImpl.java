package marathons.utils.dot;

public class DotVertexImpl implements DotVertex {
	private String id;
	private String label = "";
	private String shape = "";
	private String style = "";
	private String color = "";
	private String fillcolor = "";
	private int peripheries = 0;
	private double penWidth = 0;

	public DotVertexImpl(String id) {
		this.id = id;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public String shape() {
		return shape;
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
	public String fillcolor() {
		return fillcolor;
	}

	@Override
	public int peripheries() {
		return peripheries;
	}

	@Override
	public double penWidth() {
		return penWidth;
	}

	public DotVertexImpl setLabel(String label) {
		this.label = label;
		return this;
	}

	public DotVertexImpl setShape(String shape) {
		this.shape = shape;
		return this;
	}

	public DotVertexImpl setStyle(String style) {
		this.style = style;
		return this;
	}

	public DotVertexImpl setColor(String color) {
		this.color = color;
		return this;
	}

	public DotVertexImpl setFillcolor(String fillcolor) {
		this.fillcolor = fillcolor;
		return this;
	}

	public DotVertexImpl setPeripheries(int peripheries) {
		this.peripheries = peripheries;
		return this;
	}

	public DotVertexImpl setPenWidth(double penWidth) {
		this.penWidth = penWidth;
		return this;
	}

	@Override
	public String toString() {
		return DotGraphPrinter.toString(this);
	}
}
