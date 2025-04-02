package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class LoLSummonerSpellDto {
    private String type;
    private String version;
    private Map<String, SummonerSpell> data;

    @Getter
    @Builder
    private static class SummonerSpell {
        private String id;
        private String name;
        private String description;
        private String tooltip;
        private int maxrank;
        private List<Integer> cooldown;
        private String cooldownBurn;
        private List<Integer> cost;
        private String costBurn;
        private List<List<Integer>> effect;
        private List<String> effectBurn;
        // Vars
        private String key;
        private int summonerLevel;
        private List<String> modes;
        private String costType;
        private String maxammo;
        private List<Integer> range;
        private String rangeBurn;
        private ImageDetail image;
        private String resource;
    }

    @Getter
    @Builder
    private static class ImageDetail {
        private String full;
        private String sprite;
        private String group;
        private int x;
        private int y;
        private int w;
        private int h;
    }
}
