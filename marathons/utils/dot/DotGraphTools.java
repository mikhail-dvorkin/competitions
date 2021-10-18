package marathons.utils.dot;

import java.util.*;

public class DotGraphTools {
	public static DotGraph subGraph(DotGraph graph, Collection<? extends DotVertex> vertexSubset) {
		Set<String> ids = new HashSet<String>();
		List<DotVertex> vertices = new ArrayList<DotVertex>();
		List<DotEdge> edges = new ArrayList<DotEdge>();
		for (DotVertex dotVertex : vertexSubset) {
			ids.add(dotVertex.id());
			vertices.add(dotVertex);
		}
		for (DotEdge edge : graph.edges()) {
			if (ids.contains(edge.from()) && ids.contains(edge.to())) {
				edges.add(edge);
			}
		}
		return new DotGraphImpl(vertices, edges);
	}

	public static DotGraph filterEdges(DotGraph graph, double minPenWidth) {
		List<DotEdge> edges = new ArrayList<DotEdge>();
		for (DotEdge edge : graph.edges()) {
			if (edge.penWidth() < minPenWidth) {
				continue;
			}
			edges.add(edge);
		}
		return new DotGraphImpl(graph.vertices(), edges);
	}
}
