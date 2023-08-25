package com.example.Test.model;

public class ApiResponse {
    private final String message;
    private final Member member;

    public ApiResponse(String message, Member member) {
        this.message = message;
        this.member = member;
    }

    public String getMessage() {
        return message;
    }

    public Member getMember() {
        return member;
    }
}
