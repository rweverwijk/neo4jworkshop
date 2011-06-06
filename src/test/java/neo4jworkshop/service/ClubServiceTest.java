package neo4jworkshop.service;

import neo4jworkshop.util.AbstractNeo4JExcercise;
import neo4jworkshop.model.Member;
import neo4jworkshop.model.Team;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


public class ClubServiceTest extends AbstractNeo4JExcercise {
    private ClubService clubService;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        clubService = new ClubService(getGraphDatabaseService());
    }

    @Test
    public void memberObjectCanBeCreated() {
        Member member = clubService.createMember();
        assertNotNull(member);
    }

    @Test
    public void teamsCanBeFound() {
        Team s3 = clubService.getTeam("150");
        assertEquals("S3", s3.getName());
    }

    @Test
    public void membersCanBeFound() {
        Member ron = clubService.getMember("NDQ09H4");
        assertEquals("Ron van Weverwijk", ron.getName());
    }

    @Test
    public void playerMostConnections() {

    }
}
