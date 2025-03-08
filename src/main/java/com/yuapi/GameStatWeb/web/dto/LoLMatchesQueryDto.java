package com.yuapi.GameStatWeb.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoLMatchesQueryDto {

    private Long startTime;
    private Long endTime;
    private int queue;
    private String type;
    private int start;
    @Builder.Default
    private int count = 20;
}
