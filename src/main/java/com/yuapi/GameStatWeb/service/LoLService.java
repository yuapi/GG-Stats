package com.yuapi.GameStatWeb.service;

import com.yuapi.GameStatWeb.domain.enums.QueueType;
import com.yuapi.GameStatWeb.web.dto.*;
import com.yuapi.GameStatWeb.web.dto.lol.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class LoLService {

    @Value("${api.key.lol}")
    private String lolApiKey;

    private final RestTemplate restTemplate;

    // Using in Match API
    private static final Map<String, String> regionMap = Map.ofEntries(
            // Americas
            Map.entry("na1", "americas"),
            Map.entry("br1", "americas"),
            Map.entry("la1", "americas"),
            Map.entry("la2", "americas"),

            // Asia
            Map.entry("kr", "asia"),
            Map.entry("jp1", "asia"),

            // Europe
            Map.entry("eun1", "europe"),
            Map.entry("euw1", "europe"),
            Map.entry("me1", "asia"),
            Map.entry("tr1", "europe"),
            Map.entry("ru", "europe"),

            // South East Asia
            Map.entry("oc1", "sea"),
            Map.entry("sg2", "sea"),
            Map.entry("tw2", "sea"),
            Map.entry("vn2", "sea")
    );

    private UriComponentsBuilder defaultUriBuilder(String region, String endpoint) {
        return UriComponentsBuilder.fromUriString("https://" + region + ".api.riotgames.com" + endpoint)
                .queryParam("api_key", lolApiKey);
    }

    @Transactional
    public String getCurrentVersion() {
        String url = "https://ddragon.leagueoflegends.com/api/versions.json";

        String[] versions = restTemplate.getForObject(url, String[].class);

        if (versions == null || versions.length == 0) return null;
        else return versions[0];
    }

    @Transactional
    public LoLRealmsDto getRealmsByRegion(String region) {
        String url = "https://ddragon.leagueoflegends.com/realms/" + region + ".json";

        return restTemplate.getForObject(url, LoLRealmsDto.class);
    }

    @Transactional
    public RiotAccountDto getAccountByRiotId(String gameName, String tagLine) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder("asia", "/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), RiotAccountDto.class);
    }

    @Transactional
    public LoLSummonerDto getSummonerByPuuid(String region, String puuid) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/summoner/v4/summoners/by-puuid/" + puuid);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLSummonerDto.class);
    }

    @Transactional
    public String[] getMatchIdsByPuuid(String region, String puuid, LoLMatchIdsQueryDto query) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(regionMap.get(region), "/lol/match/v5/matches/by-puuid/" + puuid + "/ids");

        if (query.getStartTime() != null) {
            uriBuilder.queryParam("startTime", query.getStartTime());
        }
        if (query.getEndTime() != null) {
            uriBuilder.queryParam("endTime", query.getEndTime());
        }
        if (query.getQueue() != 0) {
            uriBuilder.queryParam("queue", query.getQueue());
        }
        if (query.getType() != null) {
            uriBuilder.queryParam("type", query.getType());
        }
        uriBuilder.queryParam("start", query.getStart());
        uriBuilder.queryParam("count", query.getCount());

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), String[].class);
    }

    @Transactional
    public LoLMatchDto getMatchById(String region, String matchId) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(regionMap.get(region), "/lol/match/v5/matches/" + matchId);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLMatchDto.class);
    }

    @Transactional
    public LoLLeagueListDto getChallengerLeague(String region, QueueType queueType) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/challengerleagues/by-queue/" + queueType.getValue());

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLLeagueListDto.class);
    }

    @Transactional
    public LoLLeagueListDto getGrandmasterLeague(String region, QueueType queueType) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/grandmasterleagues/by-queue/" + queueType.getValue());

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLLeagueListDto.class);
    }

    @Transactional
    public LoLLeagueListDto getMasterLeague(String region, QueueType queueType) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/masterleagues/by-queue/" + queueType.getValue());

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLLeagueListDto.class);
    }

    @Transactional
    public LoLLeagueEntryDto[] getLeagueEntriesByPuuid(String region, String puuid) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/league/v4/entries/by-puuid/" + puuid);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLLeagueEntryDto[].class);
    }

    @Transactional
    public LoLChampionInfoDto getChampionRotations(String region) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/platform/v3/champion-rotations");

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLChampionInfoDto.class);
    }

    @Transactional
    public LoLCurrentGameInfoDto getCurrentGame(String region, String puuid) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/spectator/v5/active-games/by-summoner/" + puuid);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLCurrentGameInfoDto.class);
    }

    @Transactional
    public LoLPlayerDto[] getClashPlayer(String region, String puuid) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/clash/v1/players/by-puuid/" + puuid);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLPlayerDto[].class);
    }

    @Transactional
    public LoLTeamDto getClashTeam(String region, String teamId) {
        UriComponentsBuilder uriBuilder = defaultUriBuilder(region, "/lol/clash/v1/teams/" + teamId);

        return restTemplate.getForObject(uriBuilder.build(false).toUriString(), LoLTeamDto.class);
    }
}
