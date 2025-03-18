package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoLSummonerDto {

    private final String accountId;
    private final int profileIconId;
    private final Long revisionDate;
    private final String id;
    private final String puuid;
    private final Long summonerLevel;
}
