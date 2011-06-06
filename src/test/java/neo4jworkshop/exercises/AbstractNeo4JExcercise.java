package neo4jworkshop.exercises;

import neo4jworkshop.service.ClubService;
import neo4jworkshop.util.NeoDatabaseCreator;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.neo4j.graphdb.*;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public abstract class AbstractNeo4JExcercise {

    private GraphDatabaseService graphDatabaseService;
    private File dbDir;
    private Transaction transaction;
    protected ClubService clubService;

    @Before
    public void setUp() throws Exception {
        dbDir = File.createTempFile("neoworkshop", "test");
        dbDir.delete();
        dbDir.mkdir();

        graphDatabaseService = new NeoDatabaseCreator().createNewDatabase(dbDir);

        assertEquals(441L, ((EmbeddedGraphDatabase) graphDatabaseService).getConfig().getGraphDbModule().getNodeManager().getNumberOfIdsInUse(Node.class));
        assertEquals(428L, ((EmbeddedGraphDatabase) graphDatabaseService).getConfig().getGraphDbModule().getNodeManager().getNumberOfIdsInUse(Relationship.class));

        clubService = new ClubService(graphDatabaseService);
        transaction = graphDatabaseService.beginTx();

    }

    @After
    public void tearDown() throws Exception {
        transaction.success();
        transaction.finish();
        graphDatabaseService.shutdown();
        FileUtils.forceDelete(dbDir);
    }

    protected GraphDatabaseService getGraphDatabaseService() {
        return graphDatabaseService;
    }
}
