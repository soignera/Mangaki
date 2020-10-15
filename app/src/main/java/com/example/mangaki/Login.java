package com.example.mangaki;

public class Login {
    private String csrfmiddlewaretoken;
    private String login;
    private String password;

    public Login(String csrfmiddlewaretoken, String login, String password) {
        this.csrfmiddlewaretoken = csrfmiddlewaretoken;
        this.login = login;
        this.password = password;
    }
}
