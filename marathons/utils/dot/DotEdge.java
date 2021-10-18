package marathons.utils.dot;

public interface DotEdge {
	public String from();
	public String to();
	public String label();
	public double penWidth();
	public String style();
	public String color();
	public int weight();
	public String dir();
	public String constraint();
}
