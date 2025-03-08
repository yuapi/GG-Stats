package com.yuapi.GameStatWeb.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RiotAccountDto {

    private final String puuid;
    private final String gameName;
    private final String tagLine;
}
