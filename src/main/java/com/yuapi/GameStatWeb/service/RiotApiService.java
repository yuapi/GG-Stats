package com.yuapi.GameStatWeb.service;

import com.yuapi.GameStatWeb.web.dto.LoLSummonerDto;
import com.yuapi.GameStatWeb.web.dto.RiotAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RiotApiService {

    @Value("${riot.api.key}")
    private String riotApiKey;

    private final RestTemplate restTemplate;

    private static final Map<String, String> regionMap = Map.ofEntries(
            // Americas
            Map.entry("br1", "americas"),
            Map.entry("la1", "americas"),
            Map.entry("la2", "americas"),
            Map.entry("na1", "americas"),

            // Asia
            Map.entry("kr", "asia"),
            Map.entry("jp1", "asia"),
            Map.entry("oc1", "asia"),
            Map.entry("sg2", "asia"),
            Map.entry("tw2", "asia"),
            Map.entry("vn2", "asia"),
            Map.entry("me1", "asia"),

            // Europe
            Map.entry("eun1", "europe"),
            Map.entry("euw1", "europe"),
            Map.entry("tr1", "europe"),
            Map.entry("ru", "europe")
    );

    private String generateUrl(String region, String endpoint) {
        return "https://" + region + ".api.riotgames.com" + endpoint + "?api_key=" + riotApiKey;
    }

    @Transactional
    public RiotAccountDto getAccountByRiotId(String region, String gameName, String tagLine) {

        String url = generateUrl(regionMap.get(region), "/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine);

        return restTemplate.getForObject(url, RiotAccountDto.class);
    }

    @Transactional
    public LoLSummonerDto getLoLSummonerByPuuid(String region, String puuid) {
        String url = generateUrl(region, "/lol/summoner/v4/summoners/by-puuid/" + puuid);

        return restTemplate.getForObject(url, LoLSummonerDto.class);
    }
}
