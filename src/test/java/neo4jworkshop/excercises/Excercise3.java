package neo4jworkshop.excercises;

import neo4jworkshop.model.Member;
import neo4jworkshop.model.Team;
import neo4jworkshop.util.AbstractNeo4JExcercise;
import org.junit.Test;
import org.neo4j.graphdb.Path;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class Excercise3 extends AbstractNeo4JExcercise{
    @Test
    public void ConnectionBetweenDirectNodes() {
        Member ron = clubService.getMember("NDQ09H4");
        Member mathieu = clubService.getMember("NDP69D5");
        Team S3 = clubService.getTeam("150");
        Path path = clubService.getPathBetween(ron.getNode(), mathieu.getNode());
        assertNotNull("Ron and Mathieu should be connected", path);
        assertEquals(2, path.length());
        assertEquals("S3", new Team(path.lastRelationship().getOtherNode(mathieu.getNode())).getName());
    }

    @Test
    public void ConnectionBetweenNodes() {
        Member dennis = clubService.getMember("NGN03F0");
        Member mathieu = clubService.getMember("NDP69D5");
        Team S3 = clubService.getTeam("150");
        Path path = clubService.getPathBetween(dennis.getNode(), mathieu.getNode());
        assertNotNull("Ron and Mathieu should be connected", path);
        assertEquals(4, path.length());
        assertEquals("S3", new Team(path.lastRelationship().getOtherNode(mathieu.getNode())).getName());
    }
}
