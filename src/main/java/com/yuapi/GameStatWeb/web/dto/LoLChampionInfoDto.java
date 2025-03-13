package com.yuapi.GameStatWeb.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoLChampionInfoDto {
    private int maxNewPlayerLevel;
    private List<Integer> freeChampionIdsForNewPlayers;
    private List<Integer> freeChampionIds;
}