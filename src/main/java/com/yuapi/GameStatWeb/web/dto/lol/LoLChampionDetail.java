package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class LoLChampionDetail {
    private String type;
    private String format;
    private String version;
    private Map<String, Champion> data;

    @Getter
    @Builder
    public static class Champion {
        private String id;
        private String key;
        private String name;
        private String title;
        private ImageDetail image;
        private List<Skin> skins;
        private String lore;
        private String blurb;
        private List<String> allytips;
        private List<String> enemytips;
        private List<String> tags;
        private String partype;
        private Summary info;
        private Stats stats;
        private List<Spell> spells;
        private Passive passive;
        // Recommended
    }

    @Getter
    @Builder
    public static class ImageDetail {
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
    public static class Skin {
        private String id;
        private int num;
        private String name;
        private boolean chromas;
    }

    @Getter
    @Builder
    public static class Summary {
        private int attack;
        private int defense;
        private int magic;
        private int difficulty;
    }

    @Getter
    @Builder
    public static class Stats {
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

    @Getter
    @Builder
    public static class Spell {
        private String id;
        private String name;
        private String description;
        private String tooltip;
        private LevelTip leveltip;
        private int maxrank;
        private List<Integer> cooldown;
        private String cooldownBurn;
        private List<Integer> cost;
        private String costBurn;
        // DataValues
        private List<List<Integer>> effect;
        private List<String> effectBurn;
        // Vars
        private String costType;
        private String maxammo;
        private List<Integer> range;
        private String rangeBurn;
        private ImageDetail image;
        private String resource;
    }

    @Getter
    @Builder
    public static class LevelTip {
        private List<String> label;
        private List<String> effect;
    }

    @Getter
    @Builder
    public static class Passive {
        private String name;
        private String description;
        private ImageDetail image;
    }
}
