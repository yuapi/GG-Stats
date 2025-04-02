package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class LoLChampionListDto {
    private String type;
    private String format;
    private String version;
    private Map<String, Champion> data;

    @Getter
    @Builder
    private static class Champion {
        private String version;
        private String id;
        private String key;
        private String name;
        private String title;
        private String blurb;
        private Summary info;
        private ImageDetail image;
        private List<String> tags;
        private String partype;
        private Stats stats;
    }

    @Getter
    @Builder
    private static class Summary {
        private int attack;
        private int defense;
        private int magic;
        private int difficulty;
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

    @Getter
    @Builder
    private static class Stats {
        private int hp;
        private int hpperlevel;
        private int mp;
        private int mpperlevel;
        private int movespeed;
        private int armor;
        private int armorperlevel;
        private int spellblock;
        private int spellblockperlevel;
        private int attackrange;
        private int hpregen;
        private int hpregenperlevel;
        private int mpregen;
        private int mpregenperlevel;
        private int crit;
        private int critperlevel;
        private int attackdamage;
        private int attackdamageperlevel;
        private int attackspeedperlevel;
        private int attackspeed;
    }
}
