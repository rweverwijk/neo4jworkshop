package neo4jworkshop.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team extends DelegatingNodeObject {

    public static final String TEAM_NUMBER_KEY = "number";
    public static final String TEAM_NAME_KEY = "name";

    public Team(Node node) {
        super(node);
    }

    public String getName() {
        return (String) getNode().getProperty(TEAM_NAME_KEY);
    }

    public List<Member> getMembers() {

        return Collections.emptyList();
    }
}
