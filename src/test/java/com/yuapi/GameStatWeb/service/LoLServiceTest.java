package com.yuapi.GameStatWeb.service;


import com.yuapi.GameStatWeb.web.dto.LoLMatchIdsQueryDto;
import com.yuapi.GameStatWeb.web.dto.LoLSummonerDto;
import com.yuapi.GameStatWeb.web.dto.RiotAccountDto;
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
        String puuid = "testPuuid";
        String region = "kr";
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
        String puuid = "testPuuid";
        String region = "kr";
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
}
