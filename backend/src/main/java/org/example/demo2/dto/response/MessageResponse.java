package org.example.demo2.dto.response;

public class MessageResponse extends BaseResponse {
    private final String message;

    public MessageResponse(boolean status, String message) {
        super(status);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
