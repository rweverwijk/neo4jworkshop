package neo4jworkshop.model;

import neo4jworkshop.exercises.AbstractNeo4JExcercise;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TeamTest extends AbstractNeo4JExcercise {

    @Test
    public void testTeamPlayers() {
        Team team = clubService.getTeam("150");
        List<Member> members = team.getMembers();
        assertEquals(12, members.size());

        HashSet<String> foundPlayers = new HashSet<String>();
        for (Member member : members) {
            foundPlayers.add(member.getName());
        }
        assertEquals(foundPlayers, getKnownPlayers());
        getKnownPlayers();

    }

    private HashSet<String> getKnownPlayers() {
        HashSet<String> knownPlayers = new HashSet<String>();
        knownPlayers.add("Ron van Weverwijk");
        knownPlayers.add("Mark van der Vusse");
        knownPlayers.add("Vincent Stekelenburg");
        knownPlayers.add("Mathieu van Leeuwen");
        knownPlayers.add("Ruben Visscher");
        knownPlayers.add("Niels van Houten");
        knownPlayers.add("Shirley Regts");
        knownPlayers.add("Marloes van de Geest");
        knownPlayers.add("Mariska Mettlach-Nillesen");
        knownPlayers.add("Sandra Visscher-de Stigter");
        knownPlayers.add("Diana Demmendaal - de Haas");
        knownPlayers.add("Jorinde Ultee");
        return knownPlayers;
    }
}
