package neo4jworkshop.excercises;

import neo4jworkshop.model.Member;
import neo4jworkshop.util.AbstractNeo4JExcercise;
import org.junit.Test;
import org.neo4j.graphdb.Path;

import static junit.framework.Assert.assertEquals;

public class Excercise5 extends AbstractNeo4JExcercise {
    @Test
    public void ownAllPathsBetweenNodes() {
        Member dennis = clubService.getMember("NGN03F0");
        Member mathieu = clubService.getMember("NDP69D5");

        Iterable<Path> pathIterable = clubService.ownAllPathsBetween(dennis.getNode(), mathieu.getNode());
        int numberOfPaths = 0;
        for (Path path : pathIterable) {
            numberOfPaths++;
        }
        assertEquals(557, numberOfPaths);
    }
}
