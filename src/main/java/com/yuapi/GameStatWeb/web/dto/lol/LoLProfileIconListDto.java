package com.yuapi.GameStatWeb.web.dto.lol;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class LoLProfileIconListDto {
    private String type;
    private String version;
    private Map<String, IconData> data;

    @Getter
    @Builder
    public static class IconData {
        private int id;
        private ImageDetail image;
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
}
