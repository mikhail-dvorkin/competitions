package marathons.utils.dot;

import java.util.Collection;

public interface DotGraph {
	public Collection<? extends DotVertex> vertices();
	public Collection<? extends DotEdge> edges();
}
