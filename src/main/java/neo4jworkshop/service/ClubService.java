package neo4jworkshop.service;

import neo4jworkshop.model.Member;
import neo4jworkshop.model.Team;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.StandardExpander;
import org.neo4j.kernel.Uniqueness;
import org.neo4j.kernel.impl.traversal.TraversalDescriptionImpl;

public class ClubService {
    private GraphDatabaseService graphDatabase;
    private Index<Node> index;

    public ClubService(GraphDatabaseService graphDatabase) {
        this.graphDatabase = graphDatabase;
        IndexManager index = graphDatabase.index();
        this.index = index.forNodes("NodeIdIndex");
    }

    public Member createMember() {
        return new Member(graphDatabase.createNode());
    }

    public Member getMember(String playerNumber) {
        return new Member(index.get(Member.MEMBER_NUMBER_KEY, playerNumber).getSingle());
    }

    public Team getTeam(String teamNumber) {
        return new Team(index.get(Team.TEAM_NUMBER_KEY, teamNumber).getSingle());
    }

    public Path shortestPathBetween(Node node1, Node node2) {
        return null;
    }

    public Iterable<Path> defaultAllPathsBetween(Node startNode, Node endNode) {
        return null;
    }

    public Iterable<Path> ownAllPathsBetween(Node startNode, Node endNode) {
        return null;
    }
}
