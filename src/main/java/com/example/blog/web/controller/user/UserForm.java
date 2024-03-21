package com.example.blog.web.controller.user;

public record UserForm(
        // TODO username 重複チェック
        String username,
        // TODO password 長さチェック
        String password
) {
}
