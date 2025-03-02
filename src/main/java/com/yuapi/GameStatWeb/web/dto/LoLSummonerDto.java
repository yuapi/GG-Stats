package com.yuapi.GameStatWeb.web.dto;

import lombok.Getter;

@Getter
public class LoLSummonerDto {

    private final String accountId;
    private final int profileIconId;
    private final Long revisionDate;
    private final String id;
    private final String puuid;
    private final Long summonerLevel;

    public LoLSummonerDto(String accountId, int profileIconId, Long revisionDate, String id, String puuid, Long summonerLevel) {
        this.accountId = accountId;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.id = id;
        this.puuid = puuid;
        this.summonerLevel = summonerLevel;
    }
}
