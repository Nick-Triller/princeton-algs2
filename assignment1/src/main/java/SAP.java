import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;

public class SAP {
    private final Digraph g;

    public SAP(Digraph g) {
        if (g == null) { throw new IllegalArgumentException(); }
        this.g = new Digraph(g);
    }

    /**
     * Calculates the length of shortest ancestral path between v and w
     * @param v Vertex
     * @param w Vertex
     * @return Length of shortest ancestral path or -1 if no such path
     */
    public int length(int v, int w) {
        return length(Arrays.asList(v), Arrays.asList(w));
    }

    /**
     * Finds a common ancestor that participates in shortest ancestral path
     * @param v Vertex
     * @param w Vertex
     * @return ID of a common ancestor that participates in shortest ancestral path or -1 if no such path
     */
    public int ancestor(int v, int w) {
        return ancestor(Arrays.asList(v), Arrays.asList(w));
    }

    /**
     * Finds the length of the shortest ancestral path between any vertex in v and any
     * vertex in w
     * @param v Vertices
     * @param w Vertices
     * @return Length of shortest ancestral path between any vertex in v and any vertex in w
     * or -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        Integer minLength = null;

        // linear
        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(this.g, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(this.g, w);

        // linear
        for (int vertex = 0; vertex < this.g.V(); vertex++) {
            // If reachable from at least one vertex in v and from at least one vertex in w
            if (pathsV.hasPathTo(vertex) && pathsW.hasPathTo(vertex)) {
                int length = pathsV.distTo(vertex) + pathsW.distTo(vertex);
                if (minLength == null || length < minLength) {
                    minLength = length;
                }
            }
        }

        return minLength == null ? -1 : minLength;
    }

    /**
     * Finds a common ancestor that participates in shortest ancestral path
     * @param v Vertices
     * @param w Vertices
     * @return Common ancestor that participates in shortest ancestral path or -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        // linear
        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(this.g, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(this.g, w);

        Integer sca = null;
        Integer minLength = null;

        // linear
        for (int vertex = 0; vertex < this.g.V(); vertex++) {
            // If reachable from at least one vertex in v and from at least one vertex in w
            if (pathsV.hasPathTo(vertex) && pathsW.hasPathTo(vertex)) {
                int length = pathsV.distTo(vertex) + pathsW.distTo(vertex);
                if (minLength == null || length < minLength) {
                    minLength = length;
                    sca = vertex;
                }
            }
        }

        return sca == null ? -1 : sca;
    }

    /**
     * Validates vertices (null check, range check)
     * @param vertices Vertices to validate
     */
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) { throw new IllegalArgumentException(); }
        for (Integer vertex : vertices) {
            validateVertex(vertex);
        }
    }

    /**
     * Validates vertex (null check, range check)
     * @param vertex Vertex to validate
     */
    private void validateVertex(Integer vertex) {
        if (vertex == null || vertex < 0 || vertex >= this.g.V()) {
            throw new IllegalArgumentException();
        }
    }
}
