package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoLTeamDto {
    private String id;
    private int tournamentId;
    private String name;
    private int iconId;
    private int tier;
    private String captain;
    private String abbreviation;
    private List<LoLPlayerDto> players;
}
