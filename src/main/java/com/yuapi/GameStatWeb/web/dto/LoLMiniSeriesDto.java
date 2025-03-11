package com.yuapi.GameStatWeb.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoLMiniSeriesDto {
    private int losses;
    private String progress;
    private int target;
    private int wins;
}
