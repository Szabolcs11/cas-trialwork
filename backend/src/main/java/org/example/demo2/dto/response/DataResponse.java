package org.example.demo2.dto.response;

public class DataResponse<T> extends BaseResponse {
    private final T data;

    public DataResponse(boolean status, T data) {
        super(status);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
