package paradox.community.global.util;


import paradox.community.global.dto.ApiResponse;

public class HttpUtil {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(message, null);
    }
}
