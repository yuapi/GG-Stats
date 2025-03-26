package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoLRealmsDto {
    private VersionDetail n;
    private String v;
    private String l;
    private String cdn;
    private String dd;
    private String lg;
    private String css;
    private int profileiconmax;
    private String store;

    @Getter
    @Builder
    public static class VersionDetail {
        private String item;
        private String rune;
        private String mastery;
        private String summoner;
        private String champion;
        private String profileicon;
        private String map;
        private String language;
        private String sticker;
    }
}
