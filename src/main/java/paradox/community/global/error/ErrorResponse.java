package paradox.community.global.error;

import paradox.community.global.error.exception.ErrorCode;

public record ErrorResponse(
        int status,
        String message
) {
    public ErrorResponse(ErrorCode errorCode) {
        this(
                errorCode.getStatus(),
                errorCode.getMessage()
        );
    }
}
