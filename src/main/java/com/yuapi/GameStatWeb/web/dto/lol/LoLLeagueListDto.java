package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoLLeagueListDto {
    private String leagueId;
    private List<LeagueItemDto> entries;
    private String tier;
    private String name;
    private String queue;

    @Getter
    @Builder
    public static class LeagueItemDto {
        private boolean freshBlood;
        private int wins;
        private LoLMiniSeriesDto miniSeries;
        private boolean inactive;
        private boolean veteran;
        private boolean hotStreak;
        private String rank;
        private int leaguePoints;
        private int losses;
        private String summonerId;
        private String puuid;
    }
}
