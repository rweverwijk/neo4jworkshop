package neo4jworkshop.model;

import org.neo4j.graphdb.Node;

public class Commission extends DelegatingNodeObject {
    public static final String COMMISSION_NUMBER_KEY = "number";
    public static final String COMMISSION_NAME_KEY = "name";

    public Commission(Node node) {
        super(node);
    }
}
