package neo4jworkshop.excercises;

import neo4jworkshop.model.Member;
import neo4jworkshop.util.AbstractNeo4JExcercise;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class Excercise1 extends AbstractNeo4JExcercise {

    @Test
    public void RonIsPlayingInS3() {
        Member ron = clubService.getMember("NDQ09H4");
        assertEquals("S3", ron.getMyTeam().getName());
    }


}