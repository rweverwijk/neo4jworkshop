package neo4jworkshop.exercises;

import neo4jworkshop.model.Member;
import neo4jworkshop.util.AbstractNeo4JExcercise;
import org.junit.Test;
import org.neo4j.graphdb.Path;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class Exercise4 extends AbstractNeo4JExcercise {
    @Test
    public void defaultAllPathsBetweenNodes() {
        Member dennis = clubService.getMember("NGN03F0");
        Member mathieu = clubService.getMember("NDP69D5");

        Iterable<Path> pathIterable = clubService.defaultAllPathsBetween(dennis.getNode(), mathieu.getNode());
        int numberOfPaths = 0;
        for (Path path : pathIterable) {
            numberOfPaths++;
        }
        assertEquals(557, numberOfPaths);
    }
}
