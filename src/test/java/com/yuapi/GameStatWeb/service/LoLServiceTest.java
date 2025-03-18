package com.yuapi.GameStatWeb.service;


import com.yuapi.GameStatWeb.domain.enums.QueueType;
import com.yuapi.GameStatWeb.web.dto.*;
import com.yuapi.GameStatWeb.web.dto.lol.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoLServiceTest {

    @Autowired
    private LoLService lolService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${api.key.lol}")
    private String lolApiKey;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    private UriComponentsBuilder defaultUriBuilder(String region, String endpoint) {
        return UriComponentsBuilder.fromUriString("https://" + region + ".api.riotgames.com" + endpoint)
                .queryParam("api_key", lolApiKey);
    }

    @Test
    public void testGetAccountByRiotId() {
        String gameName = "테스트";
        String tagLine = "KR1";

        String jsonResponse = """
            {
                "puuid": "testPuuid",
                "gameName": "테스트",
                "tagLine": "KR1"
            }
            """;
        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        RiotAccountDto account = lolService.getAccountByRiotId(gameName, tagLine);

        assertThat(account).isNotNull();
        assertThat(account.getPuuid()).isEqualTo("testPuuid");

        mockServer.verify();
    }

    @Test
    public void testGetSummonerByPuuid() {
        String region = "kr";
        String puuid = "testPuuid";
        String jsonResponse = """
            {
                "accountId": "testAccountId",
                "profileIconId": 1,
                "revisionDate": 1630000000000,
                "id": "testId",
                "puuid": "testPuuid",
                "summonerLevel": 30
            }
            """;
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/summoner/v4/summoners/by-puuid/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLSummonerDto summoner = lolService.getSummonerByPuuid(region, puuid);

        assertThat(summoner).isNotNull();
        assertThat(summoner.getPuuid()).isEqualTo("testPuuid");
        assertThat(summoner.getAccountId()).isEqualTo("testAccountId");
        assertThat(summoner.getProfileIconId()).isEqualTo(1);
        assertThat(summoner.getRevisionDate()).isEqualTo(1630000000000L);
        assertThat(summoner.getId()).isEqualTo("testId");
        assertThat(summoner.getSummonerLevel()).isEqualTo(30L);

        mockServer.verify();
    }

    @Test
    public void testGetMatchIdsByPuuid() {
        String region = "kr";
        String puuid = "testPuuid";
        LoLMatchIdsQueryDto query = LoLMatchIdsQueryDto.builder().build();
        String jsonResponse = """
            [
                "testMatchId1",
                "testMatchId2",
                "testMatchId3",
                "testMatchId4"
            ]
            """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/lol/match/v5/matches/by-puuid/" + puuid + "/ids");
        uriBuilder.queryParam("start", query.getStart());
        uriBuilder.queryParam("count", query.getCount());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        String[] matchIds = lolService.getMatchIdsByPuuid(region, puuid, query);

        assertThat(matchIds).isNotNull();
        assertThat(matchIds.length).isEqualTo(4);
        assertThat(matchIds[0]).isEqualTo("testMatchId1");
        assertThat(matchIds[1]).isEqualTo("testMatchId2");
        assertThat(matchIds[2]).isEqualTo("testMatchId3");
        assertThat(matchIds[3]).isEqualTo("testMatchId4");

        mockServer.verify();
    }

    @Test
    public void testGetMatchById() {
        String region = "kr";
        String matchId = "testMatchId";
        String jsonResponse = """
                {
                    "metadata": {
                        "dataVersion": "testVersion",
                        "matchId": "testMatchId",
                        "participants": [
                            "testParticipantId1",
                            "testParticipantId2",
                            "testParticipantId3"
                        ]
                    },
                    "info": {
                        "endOfGameResult": "win",
                        "gameCreation": 100,
                        "gameDuration": 50,
                        "participants": [],
                        "gameMode": "testMode"
                    }
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/lol/match/v5/matches/" + matchId);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLMatchDto match = lolService.getMatchById(region, matchId);

        assertThat(match).isNotNull();
        assertThat(match.getMetadata().getDataVersion()).isEqualTo("testVersion");
        assertThat(match.getMetadata().getMatchId()).isEqualTo("testMatchId");
        assertThat(match.getMetadata().getParticipants().get(0)).isEqualTo("testParticipantId1");
        assertThat(match.getMetadata().getParticipants().get(1)).isEqualTo("testParticipantId2");
        assertThat(match.getMetadata().getParticipants().get(2)).isEqualTo("testParticipantId3");
        assertThat(match.getInfo().getEndOfGameResult()).isEqualTo("win");
        assertThat(match.getInfo().getGameCreation()).isEqualTo(100);
        assertThat(match.getInfo().getGameDuration()).isEqualTo(50);
        assertThat(match.getInfo().getParticipants().size()).isEqualTo(0);
        assertThat(match.getInfo().getGameMode()).isEqualTo("testMode");

        mockServer.verify();
    }

    @Test
    public void testGetChallengerLeague() {
        String region = "kr";
        QueueType queueType = QueueType.RANKED_SOLO_5x5;
        String jsonResponse = """
                {
                    "leagueId": "testLeagueId",
                    "entries": [{
                        "freshBlood": false,
                        "wins": 5,
                        "puuid": "testPuuid"
                    }],
                    "tier": "challenger",
                    "name": "testChallenger",
                    "queue": "RANKED_SOLO_5x5"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/challengerleagues/by-queue/" + queueType.getValue());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueListDto challengerLeague = lolService.getChallengerLeague(region, queueType);

        assertThat(challengerLeague).isNotNull();
        assertThat(challengerLeague.getLeagueId()).isEqualTo("testLeagueId");
        assertThat(challengerLeague.getEntries().get(0).isFreshBlood()).isEqualTo(false);
        assertThat(challengerLeague.getEntries().get(0).getWins()).isEqualTo(5);
        assertThat(challengerLeague.getEntries().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(challengerLeague.getTier()).isEqualTo("challenger");
        assertThat(challengerLeague.getName()).isEqualTo("testChallenger");
        assertThat(challengerLeague.getQueue()).isEqualTo("RANKED_SOLO_5x5");

        mockServer.verify();
    }

    @Test
    public void testGetGrandmasterLeague() {
        String region = "kr";
        QueueType queueType = QueueType.RANKED_FLEX_SR;
        String jsonResponse = """
                {
                    "leagueId": "testLeagueId",
                    "entries": [{
                        "freshBlood": true,
                        "wins": 3,
                        "puuid": "testPuuid"
                    }],
                    "tier": "grandmaster",
                    "name": "testGrandmaster",
                    "queue": "RANKED_FLEX_SR"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/grandmasterleagues/by-queue/" + queueType.getValue());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueListDto grandmasterLeague = lolService.getGrandmasterLeague(region, queueType);

        assertThat(grandmasterLeague).isNotNull();
        assertThat(grandmasterLeague.getLeagueId()).isEqualTo("testLeagueId");
        assertThat(grandmasterLeague.getEntries().get(0).isFreshBlood()).isEqualTo(true);
        assertThat(grandmasterLeague.getEntries().get(0).getWins()).isEqualTo(3);
        assertThat(grandmasterLeague.getEntries().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(grandmasterLeague.getTier()).isEqualTo("grandmaster");
        assertThat(grandmasterLeague.getName()).isEqualTo("testGrandmaster");
        assertThat(grandmasterLeague.getQueue()).isEqualTo("RANKED_FLEX_SR");

        mockServer.verify();
    }

    @Test
    public void testGetMasterLeague() {
        String region = "kr";
        QueueType queueType = QueueType.RANKED_FLEX_TT;
        String jsonResponse = """
                {
                    "leagueId": "testLeagueId",
                    "entries": [{
                        "freshBlood": false,
                        "wins": 1,
                        "puuid": "testPuuid"
                    }],
                    "tier": "master",
                    "name": "testMaster",
                    "queue": "RANKED_FLEX_TT"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/masterleagues/by-queue/" + queueType.getValue());

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueListDto masterLeague = lolService.getMasterLeague(region, queueType);

        assertThat(masterLeague).isNotNull();
        assertThat(masterLeague.getLeagueId()).isEqualTo("testLeagueId");
        assertThat(masterLeague.getEntries().get(0).isFreshBlood()).isEqualTo(false);
        assertThat(masterLeague.getEntries().get(0).getWins()).isEqualTo(1);
        assertThat(masterLeague.getEntries().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(masterLeague.getTier()).isEqualTo("master");
        assertThat(masterLeague.getName()).isEqualTo("testMaster");
        assertThat(masterLeague.getQueue()).isEqualTo("RANKED_FLEX_TT");

        mockServer.verify();
    }

    @Test
    public void testGetLeagueEntriesByPuuid() {
        String region = "kr";
        String puuid = "testPuuid";
        String jsonResponse = """
                [
                    {
                        "leagueId": "testLeagueId1",
                        "queueType": "RANKED_SOLO_5x5",
                        "tier": "PLATINUM",
                        "rank": "III",
                        "summonerId": "testSummonerId",
                        "puuid": "testPuuid",
                        "leaguePoints": 12,
                        "wins": 11,
                        "losses": 10,
                        "veteran": false,
                        "inactive": false,
                        "freshBlood": false,
                        "hotStreak": false
                    },
                    {
                        "leagueId": "testLeagueId2",
                        "queueType": "RANKED_FLEX_SR",
                        "tier": "EMERALD",
                        "rank": "I",
                        "summonerId": "testSummonerId",
                        "puuid": "testPuuid",
                        "leaguePoints": 64,
                        "wins": 20,
                        "losses": 13,
                        "veteran": false,
                        "inactive": false,
                        "freshBlood": false,
                        "hotStreak": false
                    }
                ]
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/entries/by-puuid/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLLeagueEntryDto[] entries = lolService.getLeagueEntriesByPuuid(region, puuid);

        assertThat(entries).isNotNull();
        assertThat(entries.length).isEqualTo(2);
        assertThat(entries[0].getLeagueId()).isEqualTo("testLeagueId1");
        assertThat(entries[0].getQueueType()).isEqualTo("RANKED_SOLO_5x5");
        assertThat(entries[0].getTier()).isEqualTo("PLATINUM");
        assertThat(entries[0].getRank()).isEqualTo("III");
        assertThat(entries[0].getSummonerId()).isEqualTo("testSummonerId");
        assertThat(entries[0].getPuuid()).isEqualTo("testPuuid");
        assertThat(entries[1].getLeagueId()).isEqualTo("testLeagueId2");
        assertThat(entries[1].getQueueType()).isEqualTo("RANKED_FLEX_SR");
        assertThat(entries[1].getTier()).isEqualTo("EMERALD");
        assertThat(entries[1].getRank()).isEqualTo("I");
        assertThat(entries[1].getSummonerId()).isEqualTo("testSummonerId");
        assertThat(entries[1].getPuuid()).isEqualTo("testPuuid");

        mockServer.verify();
    }

    @Test
    public void testGetChampionRotations() {
        String region = "kr";
        String responseJson = """
                {
                    "freeChampionIds": [
                        12,
                        24,
                        25,
                        29,
                        30,
                        38,
                        50,
                        68,
                        74,
                        75,
                        81,
                        103,
                        115,
                        119,
                        127,
                        136,
                        234,
                        240,
                        876,
                        902
                    ],
                    "freeChampionIdsForNewPlayers": [
                        222,
                        254,
                        33,
                        82,
                        131,
                        350,
                        54,
                        17,
                        18,
                        37,
                        51,
                        145,
                        134,
                        89,
                        875,
                        80,
                        115,
                        91,
                        113,
                        112
                    ],
                    "maxNewPlayerLevel": 10
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/platform/v3/champion-rotations");

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLChampionInfoDto championRotations = lolService.getChampionRotations(region);

        assertThat(championRotations).isNotNull();
        assertThat(championRotations.getFreeChampionIdsForNewPlayers().size()).isEqualTo(20);
        assertThat(championRotations.getFreeChampionIdsForNewPlayers().size()).isEqualTo(20);
        assertThat(championRotations.getMaxNewPlayerLevel()).isEqualTo(10);
    }

    @Test
    public void testGetCurrentGame() {
        String region = "kr";
        String puuid = "testPuuid";
        String responseJson = """
                {
                    "gameId": 12,
                    "gameType": "RANK",
                    "gameStartTime": 1,
                    "mapId": 2,
                    "gameMode": "testMode"
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/spectator/v5/active-games/by-summoner/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLCurrentGameInfoDto currentGame = lolService.getCurrentGame(region, puuid);

        assertThat(currentGame).isNotNull();
        assertThat(currentGame.getGameId()).isEqualTo(12);
        assertThat(currentGame.getGameType()).isEqualTo("RANK");
        assertThat(currentGame.getGameStartTime()).isEqualTo(1);
        assertThat(currentGame.getMapId()).isEqualTo(2);
        assertThat(currentGame.getGameMode()).isEqualTo("testMode");

        mockServer.verify();
    }

    @Test
    public void testGetClashPlayer() {
        String region = "kr";
        String puuid = "testPuuid";
        String responseJson = """
                [
                    {
                        "summonerId": "testSummonerId",
                        "puuid": "testPuuid",
                        "teamId": "testTeamId",
                        "position": "TOP",
                        "role": "MEMBER"
                    }
                ]
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/clash/v1/players/by-puuid/" + puuid);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLPlayerDto[] players = lolService.getClashPlayer(region, puuid);

        assertThat(players).isNotNull();
        assertThat(players[0].getSummonerId()).isEqualTo("testSummonerId");
        assertThat(players[0].getPuuid()).isEqualTo("testPuuid");
        assertThat(players[0].getTeamId()).isEqualTo("testTeamId");
        assertThat(players[0].getPosition()).isEqualTo("TOP");
        assertThat(players[0].getRole()).isEqualTo("MEMBER");

        mockServer.verify();
    }

    @Test
    public void testGetClashTeam() {
        String region = "kr";
        String teamId = "testTeamId";
        String responseJson = """
                {
                    "id": "testTeamId",
                    "tournamentId": 1,
                    "name": "testTeam",
                    "iconId": 1,
                    "tier": 3,
                    "captain": "captainId",
                    "abbreviation": "abbreviation",
                    "players": [
                        {
                            "summonerId": "testSummonerId",
                            "puuid": "testPuuid",
                            "teamId": "testTeamId",
                            "position": "BOTTOM",
                            "role": "CAPTAIN"
                        }
                    ]
                }
                """;

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/clash/v1/teams/" + teamId);

        mockServer.expect(requestTo(uriBuilder.toUriString()))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        LoLTeamDto team = lolService.getClashTeam(region, teamId);

        assertThat(team).isNotNull();
        assertThat(team.getId()).isEqualTo("testTeamId");
        assertThat(team.getTournamentId()).isEqualTo(1);
        assertThat(team.getName()).isEqualTo("testTeam");
        assertThat(team.getIconId()).isEqualTo(1);
        assertThat(team.getTier()).isEqualTo(3);
        assertThat(team.getCaptain()).isEqualTo("captainId");
        assertThat(team.getAbbreviation()).isEqualTo("abbreviation");
        assertThat(team.getPlayers().get(0).getSummonerId()).isEqualTo("testSummonerId");
        assertThat(team.getPlayers().get(0).getPuuid()).isEqualTo("testPuuid");
        assertThat(team.getPlayers().get(0).getTeamId()).isEqualTo("testTeamId");
        assertThat(team.getPlayers().get(0).getPosition()).isEqualTo("BOTTOM");
        assertThat(team.getPlayers().get(0).getRole()).isEqualTo("CAPTAIN");

        mockServer.verify();
    }
}
