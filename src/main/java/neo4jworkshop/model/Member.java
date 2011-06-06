package neo4jworkshop.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;

public class Member extends DelegatingNodeObject {

    public static final String MEMBER_NAME_KEY = "name";
    public static final String MEMBER_NUMBER_KEY = "number";

    public Member(Node node) {
        super(node);
    }

    public Team getMyTeam() {
        return new Team(getNode().getSingleRelationship(DynamicRelationshipType.withName("player_of"), Direction.OUTGOING).getEndNode());
    }

    public String getName() {
        return (String) getNode().getProperty(MEMBER_NAME_KEY);
    }
}