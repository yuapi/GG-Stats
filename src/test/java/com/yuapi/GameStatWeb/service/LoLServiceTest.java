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
    public void testGetCurrentVersion() {
        String jsonResponse = """
                ["15.6.1","15.5.1","15.4.1","15.3.1","15.2.1","15.1.1","14.24.1","14.23.1","14.22.1","14.21.1"]
                """;

        String url = "https://ddragon.leagueoflegends.com/api/versions.json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        String currentVersion = lolService.getCurrentVersion();

        assertThat(currentVersion).isEqualTo("15.6.1");

        mockServer.verify();
    }

    @Test
    public void testGetRealmsByRegion() {
        String region = "kr";
        String jsonResponse = "{\"n\":{\"item\":\"15.6.1\",\"rune\":\"7.23.1\",\"mastery\":\"7.23.1\",\"summoner\":\"15.6.1\",\"champion\":\"15.6.1\",\"profileicon\":\"15.6.1\",\"map\":\"15.6.1\",\"language\":\"15.6.1\",\"sticker\":\"15.6.1\"},\"v\":\"15.6.1\",\"l\":\"ko_KR\",\"cdn\":\"https://ddragon.leagueoflegends.com/cdn\",\"dd\":\"15.6.1\",\"lg\":\"15.6.1\",\"css\":\"15.6.1\",\"profileiconmax\":28,\"store\":null}";

        String url = "https://ddragon.leagueoflegends.com/realms/" + region + ".json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLRealmsDto realms = lolService.getRealmsByRegion(region);

        assertThat(realms.getN().getItem()).isEqualTo("15.6.1");
        assertThat(realms.getN().getRune()).isEqualTo("7.23.1");
        assertThat(realms.getN().getMastery()).isEqualTo("7.23.1");
        assertThat(realms.getN().getSummoner()).isEqualTo("15.6.1");
        assertThat(realms.getN().getChampion()).isEqualTo("15.6.1");
        assertThat(realms.getN().getProfileicon()).isEqualTo("15.6.1");
        assertThat(realms.getN().getMap()).isEqualTo("15.6.1");
        assertThat(realms.getN().getLanguage()).isEqualTo("15.6.1");
        assertThat(realms.getN().getSticker()).isEqualTo("15.6.1");
        assertThat(realms.getV()).isEqualTo("15.6.1");
        assertThat(realms.getL()).isEqualTo("ko_KR");
        assertThat(realms.getCdn()).isEqualTo("https://ddragon.leagueoflegends.com/cdn");

        mockServer.verify();
    }

    @Test
    public void testGetChampions() {
        String version = "15.6.1";
        String language = "ko_KR";
        String jsonResponse = """
                {"type":"champion","format":"standAloneComplex","version":"15.6.1","data":{"Aatrox":{"version":"15.6.1","id":"Aatrox","key":"266","name":"아트록스","title":"다르킨의 검","blurb":"한때는 공허에 맞서 싸웠던 슈리마의 명예로운 수호자 아트록스와 그의 종족은 결국 공허보다 위험한 존재가 되어 룬테라의 존속을 위협했지만, 교활한 필멸자의 마법에 속아넘어가 패배하게 되었다. 수백 년에 걸친 봉인 끝에, 아트록스는 자신의 정기가 깃든 마법 무기를 휘두르는 어리석은 자들을 타락시키고 육신을 바꾸는 것으로 다시 한번 자유의 길을 찾아내었다. 이제 이전의 잔혹한 모습을 닮은 육체를 차지한 아트록스는 세상의 종말과 오랫동안 기다려온 복수를...","info":{"attack":8,"defense":4,"magic":3,"difficulty":4},"image":{"full":"Aatrox.png","sprite":"champion0.png","group":"champion","x":0,"y":0,"w":48,"h":48},"tags":["Fighter"],"partype":"피의 샘","stats":{"hp":650,"hpperlevel":114,"mp":0,"mpperlevel":0,"movespeed":345,"armor":38,"armorperlevel":4.8,"spellblock":32,"spellblockperlevel":2.05,"attackrange":175,"hpregen":3,"hpregenperlevel":0.5,"mpregen":0,"mpregenperlevel":0,"crit":0,"critperlevel":0,"attackdamage":60,"attackdamageperlevel":5,"attackspeedperlevel":2.5,"attackspeed":0.651}},"Ahri":{"version":"15.6.1","id":"Ahri","key":"103","name":"아리","title":"구미호","blurb":"영혼 세계의 마법과 선천적으로 연결된 아리는 먹잇감의 감정을 조종하고 정수를 집어삼킬 수 있는 여우 모습의 바스타야로, 영혼을 먹어 치울 때마다 그 안에 담긴 지혜와 기억의 편린을 받아들인다. 강력하면서도 불안정한 포식자였던 아리는 이제 조상들의 흔적을 찾아 세상을 여행하며 지금껏 훔친 기억을 버리고 스스로 자신만의 추억을 쌓으려 한다.","info":{"attack":3,"defense":4,"magic":8,"difficulty":5},"image":{"full":"Ahri.png","sprite":"champion0.png","group":"champion","x":48,"y":0,"w":48,"h":48},"tags":["Mage","Assassin"],"partype":"마나","stats":{"hp":590,"hpperlevel":104,"mp":418,"mpperlevel":25,"movespeed":330,"armor":21,"armorperlevel":4.7,"spellblock":30,"spellblockperlevel":1.3,"attackrange":550,"hpregen":2.5,"hpregenperlevel":0.6,"mpregen":8,"mpregenperlevel":0.8,"crit":0,"critperlevel":0,"attackdamage":53,"attackdamageperlevel":3,"attackspeedperlevel":2.2,"attackspeed":0.668}},"Akali":{"version":"15.6.1","id":"Akali","key":"84","name":"아칼리","title":"섬기는 이 없는 암살자","blurb":"킨코우 결사단과 그림자의 권이라는 지위를 버린 아칼리는 아이오니아인들에게 필요한 강력한 무기가 되어 홀로 싸우고 있다. 스승 쉔의 가르침을 잊지 않은 채 아이오니아의 적을 하나씩 암살하기로 맹세했다. 아칼리의 살행은 은밀할지 모르나 그녀의 메시지는 대륙 전체를 뒤흔든다. ''경외하라. 나는 섬기는 이 없는 암살자다.''","info":{"attack":5,"defense":3,"magic":8,"difficulty":7},"image":{"full":"Akali.png","sprite":"champion0.png","group":"champion","x":96,"y":0,"w":48,"h":48},"tags":["Assassin"],"partype":"기력","stats":{"hp":600,"hpperlevel":119,"mp":200,"mpperlevel":0,"movespeed":345,"armor":23,"armorperlevel":4.7,"spellblock":37,"spellblockperlevel":2.05,"attackrange":125,"hpregen":9,"hpregenperlevel":0.9,"mpregen":50,"mpregenperlevel":0,"crit":0,"critperlevel":0,"attackdamage":62,"attackdamageperlevel":3.3,"attackspeedperlevel":3.2,"attackspeed":0.625}},"Akshan":{"version":"15.6.1","id":"Akshan","key":"166","name":"아크샨","title":"떠도는 감시자","blurb":"상반신을 드러낸 아크샨은 위험에 직면하면 눈썹을 치켜올리며 당당한 카리스마, 정의로운 복수심으로 악과 맞서 싸운다. 전투에서 뛰어난 은신술을 발휘하며 적의 눈을 피한 후 제일 예기치 못한 순간 다시 모습을 드러낸다. 불타는 정의감과 죽음을 되돌리는 전설적인 무기로 룬테라의 수많은 악한들이 저지른 잘못을 바로잡으며 '멍청이가 되지 말자'라는 자신만의 도덕적 기준에 따라 살아간다.","info":{"attack":0,"defense":0,"magic":0,"difficulty":0},"image":{"full":"Akshan.png","sprite":"champion0.png","group":"champion","x":144,"y":0,"w":48,"h":48},"tags":["Marksman","Assassin"],"partype":"마나","stats":{"hp":630,"hpperlevel":107,"mp":350,"mpperlevel":40,"movespeed":330,"armor":26,"armorperlevel":4.7,"spellblock":30,"spellblockperlevel":1.3,"attackrange":500,"hpregen":3.75,"hpregenperlevel":0.65,"mpregen":8.2,"mpregenperlevel":0.7,"crit":0,"critperlevel":0,"attackdamage":52,"attackdamageperlevel":3,"attackspeedperlevel":4,"attackspeed":0.638}}}}
                """;

        String url = "https://ddragon.leagueoflegends.com/cdn/" + version + "/data/" + language + "/champion.json";

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        LoLChampionListDto champions = lolService.getChampions(version, language);

        assertThat(champions).isNotNull();
        assertThat(champions.getVersion()).isEqualTo(version);
        assertThat(champions.getData()).isNotNull();
        assertThat(champions.getData().size()).isEqualTo(4);
        assertThat(champions.getData().get("Aatrox").getName()).isEqualTo("아트록스");

        mockServer.verify();
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
