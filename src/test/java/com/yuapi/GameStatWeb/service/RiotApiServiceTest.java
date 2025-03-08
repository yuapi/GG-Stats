package com.yuapi.GameStatWeb.service;


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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RiotApiServiceTest {

    @Autowired
    private RiotApiService riotApiService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Value("${riot.api.key}")
    private String riotApiKey;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
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
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"
                + URLEncoder.encode(gameName, StandardCharsets.UTF_8) + "/" + URLEncoder.encode(tagLine, StandardCharsets.UTF_8) + "?api_key=" + riotApiKey;

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        RiotAccountDto account = riotApiService.getAccountByRiotId(gameName, tagLine);

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
        String url = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-puuid/"
                + puuid + "?api_key=" + riotApiKey;

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLSummonerDto summoner = riotApiService.getLoLSummonerByPuuid(region, puuid);

        assertThat(summoner).isNotNull();
        assertThat(summoner.getPuuid()).isEqualTo("testPuuid");
        assertThat(summoner.getAccountId()).isEqualTo("testAccountId");
        assertThat(summoner.getProfileIconId()).isEqualTo(1);
        assertThat(summoner.getRevisionDate()).isEqualTo(1630000000000L);
        assertThat(summoner.getId()).isEqualTo("testId");
        assertThat(summoner.getSummonerLevel()).isEqualTo(30L);

        mockServer.verify();
    }
}
