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
        PathFinder<Path> pathPathFinder = GraphAlgoFactory.shortestPath(StandardExpander.DEFAULT, 10);
        return pathPathFinder.findSinglePath(node1, node2);
    }

    public Iterable<Path> defaultAllPathsBetween(Node startNode, Node endNode) {
        PathFinder<Path> pathPathFinder = GraphAlgoFactory.allPaths(StandardExpander.DEFAULT, 10);
        return pathPathFinder.findAllPaths(startNode, endNode);
    }

    public Iterable<Path> ownAllPathsBetween(Node startNode, Node endNode) {
        return createTraversalDescription(endNode).traverse(startNode);
    }

    protected TraversalDescription createTraversalDescription(final Node endNode) {
        TraversalDescription description = new TraversalDescriptionImpl()
                .depthFirst()
                .uniqueness(Uniqueness.RELATIONSHIP_PATH)
                .evaluator(new AllPathsEvaluator(endNode));

        return description;
    }

    public class AllPathsEvaluator implements Evaluator {

        private Node endNode;

        public AllPathsEvaluator(Node endNode) {

            this.endNode = endNode;
        }

        public Evaluation evaluate(Path path) {
            if (path.length() <= 10) {
                if (path.endNode().equals(endNode)) {
                    return Evaluation.INCLUDE_AND_PRUNE;
                } else {
                    return Evaluation.EXCLUDE_AND_CONTINUE;
                }
            }
            return Evaluation.EXCLUDE_AND_PRUNE;
        }
    }
}
