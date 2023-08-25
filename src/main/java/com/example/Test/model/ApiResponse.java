package com.example.Test.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse {
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Member member;

    public ApiResponse(String message, Member member) {
        this.message = message;
        this.member = member;
    }
    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Member getMember() {
        return member;
    }
}
