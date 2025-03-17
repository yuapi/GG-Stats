package com.yuapi.GameStatWeb.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoLPlayerDto {
    private String summonerId;
    private String puuid;
    private String teamId;
    private String position;
    private String role;
}
