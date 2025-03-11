package com.yuapi.GameStatWeb.service;


import com.yuapi.GameStatWeb.domain.enums.QueueType;
import com.yuapi.GameStatWeb.web.dto.*;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "lol/league/v4/masterleagues/by-queue/" + queueType.getValue());

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
}
