package com.yuapi.GameStatWeb.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class ServerException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ServerException.class);

    private final int statusCode;
    private final String errorCode;
    private final String traceId;

    public ServerException(String message, int statusCode, String errorCode, String traceId) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.traceId = traceId;

        logger.error("ClientException 발생 - 상태 코드: {}, 에러 코드: {}, TraceID: {}", statusCode, errorCode, traceId);
    }

    public static String getMessageForStatus(int statusCode) {
        return switch (statusCode) {
            case 500 -> "서버 내부 오류가 발생했습니다.";
            case 502 -> "잘못된 게이트웨이입니다.";
            case 503 -> "서비스를 사용할 수 없습니다.";
            case 504 -> "게이트웨이 시간 초과가 발생했습니다.";
            default -> "서버 오류가 발생했습니다.";
        };
    }
}
