package com.yuapi.GameStatWeb.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum QueueType {
    RANKED_SOLO_5x5("RANKED_SOLO_5x5"),
    RANKED_FLEX_SR("RANKED_FLEX_SR"),
    RANKED_FLEX_TT("RANKED_FLEX_TT");

    @Getter
    @JsonValue
    private final String value;

    QueueType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static QueueType from(String value) {
        for (QueueType qt : QueueType.values()) {
            if (qt.value.equals(value)) {
                return qt;
            }
        }
        return null;
    }
}
