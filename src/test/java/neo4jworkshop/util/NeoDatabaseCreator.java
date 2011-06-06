package neo4jworkshop.util;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import neo4jworkshop.model.Commission;
import neo4jworkshop.model.Member;
import neo4jworkshop.model.Team;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.index.BatchInserterIndex;
import org.neo4j.index.impl.lucene.LuceneBatchInserterIndexProvider;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.batchinsert.BatchInserterImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NeoDatabaseCreator {


    private BatchInserterIndex nodeIdIndex;
    private BatchInserterImpl batchInserter;

    public EmbeddedGraphDatabase createNewDatabase(File dbDir) throws IOException {
        batchInserter = new BatchInserterImpl(dbDir.getAbsolutePath());
        LuceneBatchInserterIndexProvider luceneBatchInserterIndexProvider = new LuceneBatchInserterIndexProvider(batchInserter);
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("type", "exact");
        nodeIdIndex = luceneBatchInserterIndexProvider.nodeIndex("NodeIdIndex", config);


        importMembers();
        importTeams();
        importCommission();
        nodeIdIndex.flush();

        connectPlayers();
        connectCoaches();
        connectCommissionMembers();

        batchInserter.shutdown();
        luceneBatchInserterIndexProvider.shutdown();

        return new EmbeddedGraphDatabase(dbDir.getAbsolutePath());

    }

    private void connectPlayers() throws IOException {
        CSVReader csvReader = getCSVReader("players.csv");
        for (String[] input : csvReader.readAll()) {
            String playerNumber = input[0];
            String teamNumber = input[1];
            Long playerNodeId = nodeIdIndex.get(Member.MEMBER_NUMBER_KEY, playerNumber).getSingle();
            Long teamNodeId = nodeIdIndex.get(Team.TEAM_NUMBER_KEY, teamNumber).getSingle();
            batchInserter.createRelationship(playerNodeId, teamNodeId, DynamicRelationshipType.withName("player_of"), Collections.<String, Object>emptyMap());
        }
    }

    private void connectCoaches() throws IOException {
        CSVReader csvReader = getCSVReader("teamCoach.csv");
        for (String[] input : csvReader.readAll()) {
            String teamNumber = input[0];
            String coachNumber = input[1];
            Long coachNodeId = nodeIdIndex.get(Member.MEMBER_NUMBER_KEY, coachNumber).getSingle();
            Long teamNodeId = nodeIdIndex.get(Team.TEAM_NUMBER_KEY, teamNumber).getSingle();
            batchInserter.createRelationship(coachNodeId, teamNodeId, DynamicRelationshipType.withName("coach_of"), Collections.<String, Object>emptyMap());
        }
    }

    private void connectCommissionMembers() throws IOException {
        CSVReader csvReader = getCSVReader("commissionMember.csv");
        for (String[] input : csvReader.readAll()) {
            String commissionNumber = input[0];
            String coachNumber = input[1];
            Long coachNodeId = nodeIdIndex.get(Member.MEMBER_NUMBER_KEY, coachNumber).getSingle();
            Long commissionNodeId = nodeIdIndex.get(Commission.COMMISSION_NUMBER_KEY, commissionNumber).getSingle();
            batchInserter.createRelationship(coachNodeId, commissionNodeId, DynamicRelationshipType.withName("commission_member_of"), Collections.<String, Object>emptyMap());
        }
    }

    private void importMembers() throws IOException {
        CSVReader csvReader = getCSVReader("members.csv");
        for (String[] input : csvReader.readAll()) {
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(Member.MEMBER_NUMBER_KEY, input[0]);
            properties.put(Member.MEMBER_NAME_KEY, input[1]);
            long nodeId = batchInserter.createNode(properties);
            nodeIdIndex.add(nodeId, properties);
        }
    }

    private void importTeams() throws IOException {
        CSVReader csvReader = getCSVReader("team.csv");
        for (String[] input : csvReader.readAll()) {
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(Team.TEAM_NUMBER_KEY, input[0]);
            properties.put(Team.TEAM_NAME_KEY, input[1]);
            long nodeId = batchInserter.createNode(properties);
            nodeIdIndex.add(nodeId, properties);
        }
    }

    private void importCommission() throws IOException {
        CSVReader csvReader = getCSVReader("commission.csv");
        for (String[] input : csvReader.readAll()) {
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(Commission.COMMISSION_NUMBER_KEY, input[0]);
            properties.put(Commission.COMMISSION_NAME_KEY, input[1]);
            long nodeId = batchInserter.createNode(properties);
            nodeIdIndex.add(nodeId, properties);
        }
    }

    private CSVReader getCSVReader(String file) throws FileNotFoundException {
        return new CSVReader(new FileReader(new File(this.getClass().getClassLoader().getResource(file).getPath())), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, '\\', 1);
    }
}
