package marathons.utils.dot;

import java.util.*;

public class DotGraphImpl implements DotGraph {
	List<DotVertex> vertices;
	List<DotEdge> edges;

	public DotGraphImpl() {
		this.vertices = new ArrayList<DotVertex>();
		this.edges = new ArrayList<DotEdge>();
	}

	public DotGraphImpl(Collection<? extends DotVertex> vertices, Collection<? extends DotEdge> edges) {
		this.vertices = new ArrayList<DotVertex>(vertices);
		this.edges = new ArrayList<DotEdge>(edges);
	}

	@Override
	public Collection<DotVertex> vertices() {
		return vertices;
	}

	@Override
	public Collection<DotEdge> edges() {
		return edges;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " on " + vertices.size() + " vertices" + "; " + edges.size() + " edges";
	}
}
