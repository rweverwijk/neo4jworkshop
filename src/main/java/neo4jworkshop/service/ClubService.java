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
import org.neo4j.kernel.StandardExpander;

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
        PathFinder<Path> pathPathFinder = GraphAlgoFactory.shortestPath(StandardExpander.DEFAULT, 10);
        return pathPathFinder.findSinglePath(node1, node2);
    }

    public Iterable<Path> allPathsBetween(Node node1, Node node2) {
        PathFinder<Path> pathPathFinder = GraphAlgoFactory.allPaths(StandardExpander.DEFAULT, 10);
        return pathPathFinder.findAllPaths(node1, node2);
    }
}
