package com.example.blog.web.controller.index;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping
    public ResponseEntity<Void> index() {
        return ResponseEntity
                .noContent()
                .build();
    }
}
