package com.rookie.submit.platform.common;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.message = "success";
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.message = message;
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
