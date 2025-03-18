package com.yuapi.GameStatWeb.web.dto;

import com.yuapi.GameStatWeb.web.dto.lol.LoLMatchIdsQueryDto;
import org.junit.jupiter.api.Test;

public class LoLMatchIdsQueryDtoTest {

    @Test
    public void builderTest() {
        int queue = 100;
        Long endTime = 100L;

        LoLMatchIdsQueryDto dto = LoLMatchIdsQueryDto.builder()
                .queue(queue)
                .endTime(endTime)
                .build();

        System.out.println("startTime=" + dto.getStartTime());
        System.out.println("endTime=" + dto.getEndTime());
        System.out.println("queue=" + dto.getQueue());
        System.out.println("type=" + dto.getType());
        System.out.println("start=" + dto.getStart());
        System.out.println("count=" + dto.getCount());
    }
}
