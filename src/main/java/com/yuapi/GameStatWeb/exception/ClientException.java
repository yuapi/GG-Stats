package com.yuapi.GameStatWeb.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class ClientException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ClientException.class);

    private final int statusCode;
    private final String errorCode;
    private final String traceId;

    public ClientException(String message, int statusCode, String errorCode, String traceId) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.traceId = traceId;

        logger.error("ClientException 발생 - 상태 코드: {}, 에러 코드: {}, TraceID: {}", statusCode, errorCode, traceId);
    }

    public static String getMessageForStatus(int statusCode) {
        return switch (statusCode) {
            case 400 -> "잘못된 요청입니다.";
            case 401 -> "인증이 필요합니다.";
            case 403 -> "권한이 없습니다.";
            case 404 -> "리소스를 찾을 수 없습니다.";
            default -> "클라이언트 오류가 발생했습니다.";
        };
    }
}
