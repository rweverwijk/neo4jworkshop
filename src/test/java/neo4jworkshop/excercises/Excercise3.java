package neo4jworkshop.excercises;

import neo4jworkshop.model.Member;
import neo4jworkshop.model.Team;
import neo4jworkshop.util.AbstractNeo4JExcercise;
import org.junit.Test;
import org.neo4j.graphdb.Path;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class Excercise3 extends AbstractNeo4JExcercise {

    @Test
    public void AllPathsBetweenNodes() {
        Member dennis = clubService.getMember("NGN03F0");
        Member mathieu = clubService.getMember("NDP69D5");
        Team S3 = clubService.getTeam("150");

        Iterable<Path> pathIterable = clubService.allPathsBetween(dennis.getNode(), mathieu.getNode());
        int numberOfPaths = 0;
        for (Path path : pathIterable) {
            numberOfPaths++;
        }
        assertEquals(557, numberOfPaths);
    }
}
