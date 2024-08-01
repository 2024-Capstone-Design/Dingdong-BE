package com.seoultech.capstone.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<Void> checkHealthStatus() {
        return ResponseEntity.ok().build();
    }
}
