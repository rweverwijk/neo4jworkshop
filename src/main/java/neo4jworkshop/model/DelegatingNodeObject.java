package neo4jworkshop.model;


import org.neo4j.graphdb.Node;

public class DelegatingNodeObject {
    private Node node;

    public DelegatingNodeObject(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
