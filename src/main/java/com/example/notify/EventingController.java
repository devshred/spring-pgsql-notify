package com.example.notify;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/event")
public class EventingController {
    private static final AtomicInteger NUMBER_GENERATOR = new AtomicInteger(1);
    private final EventingService service;

    @GetMapping
    public ResponseEntity send(@RequestParam(name = "v") String value) {
        service.send(value);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/next")
    public ResponseEntity next() {
        service.send(String.valueOf(NUMBER_GENERATOR.getAndIncrement()));
        return ResponseEntity.noContent().build();
    }
}
