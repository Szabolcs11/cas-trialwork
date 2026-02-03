package org.example.demo2.dto.response;

public class BaseResponse {
    private final boolean success;

    public BaseResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
