package neo4jworkshop.model;

import neo4jworkshop.exercises.AbstractNeo4JExcercise;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MemberTest extends AbstractNeo4JExcercise {

    @Test
    public void RonIsPlayingInS3() {
        Member ron = clubService.getMember("NDQ09H4");
        assertEquals("S3", ron.getMyTeam().getName());
    }


}
