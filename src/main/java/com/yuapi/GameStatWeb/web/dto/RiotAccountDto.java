package com.yuapi.GameStatWeb.web.dto;

import lombok.Getter;

@Getter
public class RiotAccountDto {

    private final String puuid;
    private final String gameName;
    private final String tagLine;

    public RiotAccountDto(String puuid, String gameName, String tagLine) {
        this.puuid = puuid;
        this.gameName = gameName;
        this.tagLine = tagLine;
    }
}
