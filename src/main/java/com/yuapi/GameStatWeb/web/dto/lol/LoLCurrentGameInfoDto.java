package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoLCurrentGameInfoDto {
    private Long gameId;
    private String gameType;
    private Long gameStartTime;
    private Long mapId;
    private Long gameLength;
    private String platformId;
    private String gameMode;
    private List<BannedChampion> bannedChampion;
    private Long gameQueueConfigId;
    private Observer observers;
    private List<CurrentGameParticipant> participants;

    @Getter
    @Builder
    private static class BannedChampion {
        private int pickTurn;
        private Long championId;
        private Long teamId;
    }

    @Getter
    @Builder
    private static class Observer {
        private String encryptionKey;
    }

    @Getter
    @Builder
    private static class CurrentGameParticipant {
        private Long championId;
        private Perks perks;
        private Long profileIconId;
        private boolean bot;
        private Long teamId;
        private String summonerId;
        private String puuid;
        private Long spell1Id;
        private Long spell2Id;
        private List<GameCustomizationObject> gameCustomizationObjects;
    }

    @Getter
    @Builder
    private static class Perks {
        private List<Long> perkIds;
        private Long perkStyle;
        private Long perkSubStyle;
    }

    @Getter
    @Builder
    private static class GameCustomizationObject {
        private String category;
        private String content;
    }
}
