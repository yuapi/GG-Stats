package com.yuapi.GameStatWeb.web.dto.lol;

import java.util.List;

public class LoLProfileIconListDto {
    private String type;
    private String version;
    private List<IconData> data;

    private static class IconData {
        private int id;
        private ImageDetail image;
    }

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
